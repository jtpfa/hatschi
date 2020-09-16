package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown if the currently authenticated user is not authorized to perform a protected operation.
 *
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends Exception {
    private static final String ERROR_MESSAGE = "Der Nutzer ist nicht autorisiert diese Operation durchzuführen";

    public NotAuthorizedException() {
        super(ERROR_MESSAGE);
    }
}
