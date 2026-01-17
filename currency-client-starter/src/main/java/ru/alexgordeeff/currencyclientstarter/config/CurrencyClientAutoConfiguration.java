package ru.alexgordeeff.currencyclientstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;
import ru.alexgordeeff.currencyclientstarter.health.CurrencyApiHealthIndicator;
import ru.alexgordeeff.currencyclientstarter.service.CurrencyService;

@EnableRetry
@Configuration
@EnableConfigurationProperties({CurrencyStarterProperties.class})
@ConditionalOnProperty(prefix = "currency-client-starter", name = "enabled", havingValue = "true")
public class CurrencyClientAutoConfiguration {

    @Bean
    public CurrencyService currencyService(CurrencyStarterProperties properties) {
        return new CurrencyService(RestClient.builder().baseUrl(properties.getBaseUrl()).build(),
                properties.getApiKey());
    }

    @Bean
    @ConditionalOnProperty(prefix = "currency-client-starter.health", name = "enabled", havingValue = "true", matchIfMissing = true)
    public CurrencyApiHealthIndicator currencyApiHealthIndicator(CurrencyStarterProperties properties) {
        return new CurrencyApiHealthIndicator(
                RestClient.builder().baseUrl(properties.getBaseUrl()).build(),
                properties.getApiKey());
    }

    @Bean
    @ConditionalOnProperty(prefix = "currency-client-starter.retry", name = "enabled", havingValue = "true")
    public RetryTemplate retryTemplate(CurrencyStarterProperties properties) {
        var retryTemplate = new RetryTemplate();
        var fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(properties.getRetry().getBackoff());
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(properties.getRetry().getMaxAttempts()));
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        return retryTemplate;
    }
}
