package ru.alexgordeeff.currencyclientstarter.service;

import ru.alexgordeeff.currencyclientstarter.model.CurrencyRoot;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import ru.alexgordeeff.currencyclientstarter.model.ExchangeRateException;

import java.math.BigDecimal;
import java.util.Optional;

public class CurrencyService {

    private final RestClient restClient;
    private final String apiKey;

    public CurrencyService(RestClient restClient, String apiKey) {
        this.restClient = restClient;
        this.apiKey = apiKey;
    }

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        var currencyRoot = restClient.get()
                .uri("/v6/" + apiKey + "/latest/{fromCurrency}", fromCurrency)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new ExchangeRateException(String.format(
                            "Failed to fetch currency rates for %s and %s", fromCurrency, toCurrency));
                })
                .body(CurrencyRoot.class);

        if (currencyRoot == null) {
            throw new ExchangeRateException("for selected currency result is null");
        }

        if (currencyRoot.getResult().equals("error")) {
            throw new ExchangeRateException(String.format("Failed to fetch currency rates for %s and %s", fromCurrency, toCurrency));
        }

        return BigDecimal.valueOf(Optional.ofNullable(currencyRoot.getConversionRates().get(toCurrency)).orElseThrow());
    }
}
