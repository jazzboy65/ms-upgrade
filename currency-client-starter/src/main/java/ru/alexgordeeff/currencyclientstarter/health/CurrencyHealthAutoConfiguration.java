package ru.alexgordeeff.currencyclientstarter.health;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.alexgordeeff.currencyclientstarter.config.CurrencyClientAutoConfiguration;
import ru.alexgordeeff.currencyclientstarter.config.CurrencyStarterProperties;
import ru.alexgordeeff.currencyclientstarter.retry.CurrencyClientRetryAutoConfiguration;

@Configuration
@AutoConfiguration(after = {CurrencyClientAutoConfiguration.class, CurrencyClientRetryAutoConfiguration.class})
@ConditionalOnClass(HealthIndicator.class)
@ConditionalOnProperty(prefix = "currency-client-starter.health", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({CurrencyStarterProperties.class})
public class CurrencyHealthAutoConfiguration {

    @Bean
    public CurrencyApiHealthIndicator currencyApiHealthIndicator(CurrencyStarterProperties currencyStarterProperties) {
        return new CurrencyApiHealthIndicator(
                RestClient.builder().baseUrl(currencyStarterProperties.getBaseUrl()).build(),
                currencyStarterProperties.getApiKey());
    }
}
