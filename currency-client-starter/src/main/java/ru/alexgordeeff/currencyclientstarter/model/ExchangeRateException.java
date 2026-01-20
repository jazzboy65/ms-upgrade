package ru.alexgordeeff.currencyclientstarter.model;

public class ExchangeRateException extends RuntimeException {
    public ExchangeRateException(String message) {
        super(message);
    }
}
