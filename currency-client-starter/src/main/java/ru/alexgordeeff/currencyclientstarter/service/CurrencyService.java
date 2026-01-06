package ru.alexgordeeff.currencyclientstarter.service;

import ru.alexgordeeff.currencyclientstarter.model.CurrencyRoot;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import ru.alexgordeeff.currencyclientstarter.model.ExchangeRateException;

import java.math.BigDecimal;

public class CurrencyService {

    private final RestClient restClient;
    private final String apiKey;

    public CurrencyService(RestClient restClient, String apiKey) {
            this.restClient = restClient;
            this.apiKey = apiKey;
    }

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        if (fromCurrency == null || toCurrency == null || fromCurrency.isBlank() || toCurrency.isBlank()) {
            throw new ExchangeRateException("fromCurrency and toCurrency must be non-empty");
        }

        var currencyRoot = restClient.get()
                .uri("/v6/" + apiKey + "/latest/{fromCurrency}", fromCurrency)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new ExchangeRateException(String.format(
                            "Failed to fetch currency rates for %s and %s", fromCurrency, toCurrency));
                })
                .body(CurrencyRoot.class);

        return getRate(fromCurrency, currencyRoot);
    }

    private BigDecimal getRate(String toCurrency, CurrencyRoot currencyRoot) {
        if (currencyRoot == null) {
            throw new ExchangeRateException("For selected currency result is null");
        }

        if ("error".equals(currencyRoot.getResult())) {
            throw new ExchangeRateException("An error occurred while receiving the request");
        }

        var rates = currencyRoot.getConversionRates();
        if (rates == null || !rates.containsKey(toCurrency)) {
            throw new ExchangeRateException(String.format("Rate for %s not found", toCurrency));
        }

        var rate = rates.get(toCurrency);
        if (rate == null) {
            throw new ExchangeRateException(String.format("Rate value is null for %s", toCurrency));
        }
        return BigDecimal.valueOf(rate);
    }
}
