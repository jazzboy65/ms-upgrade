package alexgordeeff.ms_upgrade.exception;

import clients.model.ErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException{
    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message, HttpStatus.NOT_FOUND);
    }
}
