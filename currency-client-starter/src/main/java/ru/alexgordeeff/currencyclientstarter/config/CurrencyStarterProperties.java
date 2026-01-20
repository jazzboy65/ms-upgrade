package ru.alexgordeeff.currencyclientstarter.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "currency-client-starter")
public class CurrencyStarterProperties {
    private boolean enabled;
    private String apiKey;
    private String baseUrl;
    @NestedConfigurationProperty
    private CurrencyStarterHealthProperties health = new CurrencyStarterHealthProperties();
    @NestedConfigurationProperty
    private CurrencyStarterRetryProperties retry = new CurrencyStarterRetryProperties();

    public CurrencyStarterRetryProperties getRetry() {
        return retry;
    }

    public void setRetry(CurrencyStarterRetryProperties retry) {
        this.retry = retry;
    }

    public CurrencyStarterHealthProperties getHealth() {
        return health;
    }

    public void setHealth(CurrencyStarterHealthProperties health) {
        this.health = health;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
