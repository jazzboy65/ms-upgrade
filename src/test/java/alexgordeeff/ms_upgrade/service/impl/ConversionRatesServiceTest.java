package alexgordeeff.ms_upgrade.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;


@SpringBootTest
public class ConversionRatesServiceTest {

    @Autowired
    private ConversionRatesServiceImpl service;
    @Autowired
    private CacheManager cacheManager;

    @Test
    void shouldCacheExchangeRate() {
        service.convert("RUB", "USD");
        var cache = cacheManager.getCache("rates");
        Assertions.assertThat(cache).isNotNull();
        System.out.println(cache);
    }
}

