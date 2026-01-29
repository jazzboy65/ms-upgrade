package alexgordeeff.ms_upgrade.service.impl;

import alexgordeeff.ms_upgrade.service.ConversionRatesService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.alexgordeeff.currencyclientstarter.service.CurrencyService;

import java.math.BigDecimal;

@Slf4j
@Service
public class ConversionRatesServiceImpl implements ConversionRatesService {

    private final Counter externalApiCallsCounter;
    private final CurrencyService currencyService;

    public ConversionRatesServiceImpl(CurrencyService currencyService, MeterRegistry meterRegistry) {
        this.currencyService = currencyService;
        this.externalApiCallsCounter = Counter.builder("currency_exchange_rate_request")
                .description("External API Calls Counter")
                .register(meterRegistry);
    }

    @Override
    @Cacheable(value  = "rates", key = "#sourceCurrency + '_' + #targetCurrency")
    public BigDecimal convert(String sourceCurrency, String targetCurrency) {
        try {
            externalApiCallsCounter.increment();
            return currencyService.getExchangeRate(sourceCurrency, targetCurrency);
        } catch (RuntimeException e) {
            log.error("Failed to convert {} -> {}", sourceCurrency, targetCurrency, e);
            throw e;
        }
    }
}
