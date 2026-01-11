package ru.alexgordeeff.currencyclientstarter.retry;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
@ConditionalOnClass({RestTemplate.class, SimpleRetryPolicy.class})
@EnableConfigurationProperties(CurrencyStarterRetryProperties.class)
@ConditionalOnProperty(prefix = "currency-client-starter.retry", name = "enabled", havingValue = "true")
public class CurrencyClientRetryAutoConfiguration {

    @Bean
    public RetryTemplate retryTemplate(CurrencyStarterRetryProperties retryProperties) {
        var retryTemplate = new RetryTemplate();
        var fixedBackOffPolicy = new FixedBackOffPolicy();

        fixedBackOffPolicy.setBackOffPeriod(retryProperties.getBackoff());
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(retryProperties.getMaxAttempts()));
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        return retryTemplate;
    }
}
