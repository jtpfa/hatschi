package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PasswordException extends Exception {
    public PasswordException(String errorMessage) {
        super(errorMessage);
    }
}
