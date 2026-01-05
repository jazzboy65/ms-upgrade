package alexgordeeff.ms_upgrade.exception;

import clients.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.alexgordeeff.currencyclientstarter.model.ExchangeRateException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiError ex) {
        var error = new ErrorResponse(
                ex.getErrorCode(), ex.getMessage(), ex.getHttpStatus().value());
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        var error = new ErrorResponse(
                ApiError.RUNTIME_EXCEPTION.getErrorCode(),
                ApiError.RUNTIME_EXCEPTION.getMessage(),
                ApiError.RUNTIME_EXCEPTION.getHttpStatus().value());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(error, ApiError.RUNTIME_EXCEPTION.getHttpStatus());
    }

    @ExceptionHandler(ExchangeRateException.class)
    public ResponseEntity<String> handleException(ExchangeRateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
