package ru.alexgordeeff.currencyclientstarter.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
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

    @Retryable(retryFor = {RuntimeException.class, ExchangeRateException.class}
            , maxAttemptsExpression = "${currency-client-starter.retry.max-attempts}"
            , backoff = @Backoff(delayExpression = "${currency-client-starter.retry.backoff}")
            , label = "currency-rate")
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
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new RuntimeException("Server error occurred while fetching currency rates with status: " + response.getStatusCode());
                })
                .body(CurrencyRoot.class);

        return getRate(toCurrency, currencyRoot);
    }

    private BigDecimal getRate(String toCurrency, CurrencyRoot currencyRoot) {
        if (currencyRoot == null) {
            throw new ExchangeRateException(String.format("For currency %s result is null", toCurrency));
        }

        if ("error".equals(currencyRoot.getResult())) {
            throw new ExchangeRateException("An error occurred while receiving the request");
        }

        var rates = currencyRoot.getConversionRates();
        if (rates == null || !rates.containsKey(toCurrency)) {
            throw new ExchangeRateException(String.format("Rate for %s not found", toCurrency));
        }

        var rate = rates.get(toCurrency);
        return BigDecimal.valueOf(rate);
    }
}
