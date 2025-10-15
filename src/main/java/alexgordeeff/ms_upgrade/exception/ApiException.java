package alexgordeeff.ms_upgrade.exception;

import clients.model.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public abstract class ApiException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;

    protected ApiException(ErrorCode errorCode, String message,  HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
