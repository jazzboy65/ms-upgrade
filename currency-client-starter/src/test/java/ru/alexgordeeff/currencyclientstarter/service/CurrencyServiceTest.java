package ru.alexgordeeff.currencyclientstarter.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;
import ru.alexgordeeff.currencyclientstarter.model.CurrencyRoot;
import ru.alexgordeeff.currencyclientstarter.model.ExchangeRateException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient restClient;

    private CurrencyService currencyService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String RUB = "RUB";
    private final String USD = "USD";

    @BeforeEach
    void setUp() {
        currencyService = new CurrencyService(restClient, "test");
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    void getExchangeRate_success() throws IOException {
        var test = objectMapper.readValue(new File("src/test/resources/currency-test.json"), CurrencyRoot.class);
        var expectedRate = new BigDecimal("80.8584");
        when(restClient.get()
                .uri(anyString(),anyString())
                .retrieve()
                .onStatus(any(), any())
                .onStatus(any(), any())
                .body(CurrencyRoot.class))
                .thenReturn(test);

        var actualRate = currencyService.getExchangeRate(USD, RUB);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualRate).isEqualTo(expectedRate);
            Mockito.verify(restClient, Mockito.times(2)).get();
        });
    }

    @Test
    void getExchangeRate_fromCurrency_null() {
        assertThatThrownBy(() -> currencyService.getExchangeRate(null, RUB))
                .isInstanceOf(ExchangeRateException.class)
                .hasMessage("fromCurrency and toCurrency must be non-empty");
    }

    @Test
    void getExchangeRate_toCurrency_null() {
        assertThatThrownBy(() -> currencyService.getExchangeRate(USD, null))
                .isInstanceOf(ExchangeRateException.class)
                .hasMessage("fromCurrency and toCurrency must be non-empty");
    }

    @Test
    void getExchangeRate_fromCurrency_blank() {
        assertThatThrownBy(() -> currencyService.getExchangeRate("", RUB))
                .isInstanceOf(ExchangeRateException.class)
                .hasMessage("fromCurrency and toCurrency must be non-empty");
    }

    @Test
    void getExchangeRate_toCurrency_blank() {
        assertThatThrownBy(() -> currencyService.getExchangeRate(USD, ""))
                .isInstanceOf(ExchangeRateException.class)
                .hasMessage("fromCurrency and toCurrency must be non-empty");
    }

    @Test
    void getExchangeRate_is400ClientError() {
        var restClientBuilder = RestClient.builder();
        var mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        currencyService = new CurrencyService(restClientBuilder.build(), "test");

        mockServer.expect(MockRestRequestMatchers.requestTo("/v6/test/latest/RUB"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.BAD_REQUEST));

        assertThatThrownBy(() -> currencyService.getExchangeRate(RUB, USD))
                .isInstanceOf(ExchangeRateException.class)
                .hasMessage("Failed to fetch currency rates for RUB and USD");
    }

    @Test
    void getExchangeRate_is500ServerError() {
        var restClientBuilder = RestClient.builder();
        var mockServer = MockRestServiceServer.bindTo(restClientBuilder).build();
        currencyService = new CurrencyService(restClientBuilder.build(), "test");

        mockServer.expect(MockRestRequestMatchers.requestTo("/v6/test/latest/RUB"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThatThrownBy(() -> currencyService.getExchangeRate(RUB, USD))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Server error occurred while fetching currency rates with status: 500 INTERNAL_SERVER_ERROR");
    }


    @Test
    void getExchangeRate_currencyRoot_null() {
        when(restClient.get()
                .uri(anyString(), anyString())
                .retrieve()
                .onStatus(any(), any())
                .onStatus(any(), any())
                .body(CurrencyRoot.class))
                .thenReturn(null);

        assertThatThrownBy(() -> currencyService.getExchangeRate(RUB, USD))
                .isInstanceOf(ExchangeRateException.class)
                .hasMessage("For currency USD result is null");
    }

    @Test
    void getExchangeRate_currencyRoot_result_error() {
        when(restClient.get()
                .uri(anyString(), anyString())
                .retrieve()
                .onStatus(any(), any())
                .onStatus(any(), any())
                .body(CurrencyRoot.class))
                .thenReturn(new CurrencyRoot("error", new HashMap<>()));

        assertThatThrownBy(() -> currencyService.getExchangeRate(RUB, USD))
                .isInstanceOf(ExchangeRateException.class)
                .hasMessage("An error occurred while receiving the request");
    }

    @Test
    void getExchangeRate_currencyRoot_rates_null() {
        when(restClient.get()
                .uri(anyString(), anyString())
                .retrieve()
                .onStatus(any(), any())
                .onStatus(any(), any())
                .body(CurrencyRoot.class))
                .thenReturn(new CurrencyRoot("success", null));

        assertThatThrownBy(() -> currencyService.getExchangeRate(RUB, USD))
                .isInstanceOf(ExchangeRateException.class)
                .hasMessage("Rate for USD not found");
    }

    @Test
    void getExchangeRate_currencyRoot_rates_not_contain_expected_currency() {
        when(restClient.get()
                .uri(anyString(), anyString())
                .retrieve()
                .onStatus(any(), any())
                .onStatus(any(), any())
                .body(CurrencyRoot.class))
                .thenReturn(new CurrencyRoot("success", Map.of("CNY", 1.0)));

        assertThatThrownBy(() -> currencyService.getExchangeRate(RUB, USD))
                .isInstanceOf(ExchangeRateException.class)
                .hasMessage("Rate for USD not found");
    }
}