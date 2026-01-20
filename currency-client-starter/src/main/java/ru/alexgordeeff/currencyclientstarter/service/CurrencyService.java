package ru.alexgordeeff.currencyclientstarter.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import ru.alexgordeeff.currencyclientstarter.model.CurrencyRoot;
import org.springframework.web.client.RestClient;
import ru.alexgordeeff.currencyclientstarter.model.ExchangeRateException;

import java.math.BigDecimal;

@Service
public class CurrencyService {

    private final RestClient restClient;
    private final String apiKey;

    public CurrencyService(RestClient restClient, String apiKey) {
        this.restClient = restClient;
        this.apiKey = apiKey;
    }

    @Retryable(retryFor = {RuntimeException.class, ExchangeRateException.class}
            , noRetryFor = {IllegalArgumentException.class}
            , maxAttemptsExpression = "${currency-client-starter.retry.max-attempts}"
            , backoff = @Backoff(delayExpression = "${currency-client-starter.retry.backoff}")
            , label = "currency-rate")
    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        if (fromCurrency == null || toCurrency == null || fromCurrency.isBlank() || toCurrency.isBlank()) {
            throw new IllegalArgumentException("fromCurrency and toCurrency must be non-empty");
        }

        if (fromCurrency.equals(toCurrency)) {
            return new BigDecimal(1);
        }
        try {
            var currencyRoot = restClient.get()
                    .uri("/v6/" + apiKey + "/latest/{fromCurrency}", fromCurrency)
                    .retrieve()
                    .body(CurrencyRoot.class);

            return getRate(toCurrency, currencyRoot);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new ExchangeRateException(String.format(
                        "Failed to fetch currency rates for %s and %s", fromCurrency, toCurrency));
            }
            throw new RuntimeException("Unexpected client error: " + e.getStatusCode(), e);
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Server error occurred while fetching currency rates with status: " + e.getStatusCode());
        }
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
