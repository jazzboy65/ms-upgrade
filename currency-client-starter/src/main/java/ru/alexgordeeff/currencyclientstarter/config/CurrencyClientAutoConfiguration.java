package ru.alexgordeeff.currencyclientstarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.alexgordeeff.currencyclientstarter.service.CurrencyService;

@Configuration
@EnableConfigurationProperties(CurrencyStarterProperties.class)
public class CurrencyClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CurrencyService currencyService(CurrencyStarterProperties properties) {
        return new CurrencyService(RestClient.builder().baseUrl(properties.getBaseUrl()).build(),
                properties.getApiKey());
    }
}
