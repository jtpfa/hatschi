package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NoAddressFoundException extends Exception {
    public static final String ERROR_MESSAGE = "Es konnte keine Adresse mit der ID '%s' gefunden werden";

    public NoAddressFoundException(Long id) {
        super(String.format(ERROR_MESSAGE, id));
    }

}
