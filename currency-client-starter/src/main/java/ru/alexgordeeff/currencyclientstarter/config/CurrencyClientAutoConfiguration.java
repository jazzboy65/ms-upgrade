package ru.alexgordeeff.currencyclientstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.alexgordeeff.currencyclientstarter.service.CurrencyService;

@Configuration
@EnableConfigurationProperties(CurrencyStarterProperties.class)
@ConditionalOnProperty(prefix = "currency-client-starter", name = "enabled", havingValue = "true")
public class CurrencyClientAutoConfiguration {

    @Bean
    public CurrencyService currencyService(CurrencyStarterProperties properties) {
        return new CurrencyService(RestClient.builder().baseUrl(properties.getBaseUrl()).build(),
                properties.getApiKey());
    }
}
