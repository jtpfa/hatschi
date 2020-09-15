package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = NoArticleFoundException.ERROR_MESSAGE)
public class NoArticleFoundException extends Exception {
    public static final String ERROR_MESSAGE = "Es konnte kein Artikel mit dieser ID gefunden werden.";

    public NoArticleFoundException() {
        super(ERROR_MESSAGE);
    }
}
