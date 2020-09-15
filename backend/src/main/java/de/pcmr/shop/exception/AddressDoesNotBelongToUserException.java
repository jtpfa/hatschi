package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = AddressDoesNotBelongToUserException.ERROR_MESSAGE)
public class AddressDoesNotBelongToUserException extends Exception {
    public static final String ERROR_MESSAGE = "Die zu bearbeitende Adresse gehört nicht dem Benutzer";

    public AddressDoesNotBelongToUserException() {
        super(ERROR_MESSAGE);
    }
}
