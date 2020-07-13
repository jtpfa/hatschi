package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = NoCustomerFoundException.ERROR_MESSAGE)
public class NoCustomerFoundException extends Exception {
    public static final String ERROR_MESSAGE = "Es konnte kein Kunde zu der Anfrage gefunden werden.";

    public NoCustomerFoundException() {
        super(ERROR_MESSAGE);
    }
}
