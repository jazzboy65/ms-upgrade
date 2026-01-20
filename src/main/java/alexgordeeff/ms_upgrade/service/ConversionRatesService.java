package alexgordeeff.ms_upgrade.service;

import java.math.BigDecimal;

public interface ConversionRatesService {

    BigDecimal convert(String sourceCurrency, String targetCurrency);
}
