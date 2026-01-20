package ru.alexgordeeff.currencyclientstarter.config;

public class CurrencyStarterRetryProperties {
    private boolean enabled;
    private int maxAttempts;
    private long backoff;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public long getBackoff() {
        return backoff;
    }

    public void setBackoff(long backoff) {
        this.backoff = backoff;
    }
}
