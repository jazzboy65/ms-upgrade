package ru.alexgordeeff.currencyclientstarter.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.alexgordeeff.currencyclientstarter.health.CurrencyStarterHealthProperties;

@ConfigurationProperties(prefix = "currency-client-starter")
public class CurrencyStarterProperties {
    private boolean enabled;
    private String apiKey;
    private String baseUrl;
    private CurrencyStarterHealthProperties health = new CurrencyStarterHealthProperties();

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
