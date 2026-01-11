package ru.alexgordeeff.currencyclientstarter.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CurrencyApiHealthIndicator implements HealthIndicator {

    private final RestClient restClient;
    private final String healthEndpoint;
    private final String apiKey;

    public CurrencyApiHealthIndicator(RestClient restClient, String apiKey) {
        this.apiKey = apiKey;
        this.restClient = restClient;
        this.healthEndpoint = "v6/{apiKey}/latest/USD";
    }

    @Override
    public Health health() {
        try {
            var response = restClient.get()
                    .uri(healthEndpoint, apiKey)
                    .retrieve()
                    .toBodilessEntity();

            return response.getStatusCode().is2xxSuccessful()
                    ? Health.up()
                    .withDetail("currency-api", "UP")
                    .build()
                    : Health.down()
                    .withDetail("currency-api", "DOWN")
                    .withDetail("status", response.getStatusCode().toString())
                    .build();
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
