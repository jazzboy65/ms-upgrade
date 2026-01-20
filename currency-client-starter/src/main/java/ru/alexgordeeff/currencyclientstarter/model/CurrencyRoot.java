package ru.alexgordeeff.currencyclientstarter.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CurrencyRoot {
    @JsonProperty("result")
    private String result;
    @JsonProperty("conversion_rates")
    private Map<String, Double> conversionRates;

    @JsonCreator
    public CurrencyRoot(
            @JsonProperty("result") String result,
            @JsonProperty("conversion_rates") Map<String, Double> conversionRates) {
        this.result = result;
        this.conversionRates = conversionRates;
    }

    public String getResult() {
        return result;
    }

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }
}
