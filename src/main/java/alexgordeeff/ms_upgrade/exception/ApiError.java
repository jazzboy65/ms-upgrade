package alexgordeeff.ms_upgrade.exception;

import clients.model.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiError {
        BAD_REQUEST_EXCEPTION("Клиент не найден", ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST),
        CONFLICT_EXCEPTION("Клиент с таким mdmId уже существует/У клиента есть активные счета", ErrorCode.CONFLICT, HttpStatus.CONFLICT),
        NOT_FOUND_EXCEPTION("Клиент не найден", ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND);

        private final String message;
        private final ErrorCode errorCode;
        private final HttpStatus httpStatus;

        ApiError(String message, ErrorCode errorCode, HttpStatus httpStatus) {
                this.message = message;
                this.errorCode = errorCode;
                this.httpStatus = httpStatus;
        }
    }

