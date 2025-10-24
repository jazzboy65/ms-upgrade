package alexgordeeff.ms_upgrade.exception;

import clients.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException{
    public ConflictException(String message) {
        super(ErrorCode.CONFLICT, message, HttpStatus.CONFLICT);
    }
}
