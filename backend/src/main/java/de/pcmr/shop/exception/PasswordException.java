package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown if there was any exception during change of credentials.
 *
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PasswordException extends Exception {
    public PasswordException(String errorMessage) {
        super(errorMessage);
    }
}
