package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.CONFLICT, reason = NotEnoughArticlesOnStockException.ERROR_MESSAGE)
public class NotEnoughArticlesOnStockException extends Exception {
    public static final String ERROR_MESSAGE = "Es sind nicht genügend Artikel auf Lager.";
}
