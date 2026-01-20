package alexgordeeff.ms_upgrade.service.impl;

import alexgordeeff.ms_upgrade.service.ConversionRatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alexgordeeff.currencyclientstarter.service.CurrencyService;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversionRatesServiceImpl implements ConversionRatesService {

    private final CurrencyService currencyService;

    @Override
    public BigDecimal convert(String sourceCurrency, String targetCurrency) {
        try {
            return currencyService.getExchangeRate(sourceCurrency, targetCurrency);
        } catch (RuntimeException e) {
            log.error("Failed to convert {} -> {}", sourceCurrency, targetCurrency, e);
            throw e;
        }
    }
}
