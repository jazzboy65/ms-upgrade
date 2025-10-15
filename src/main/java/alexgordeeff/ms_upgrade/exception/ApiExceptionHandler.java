package alexgordeeff.ms_upgrade.exception;

import clients.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex ) {
        var error = new ErrorResponse(
                ex.getErrorCode(), ex.getMessage(), ex.getHttpStatus().value());
        return new ResponseEntity<>(error, ex.getHttpStatus());
    }
}
