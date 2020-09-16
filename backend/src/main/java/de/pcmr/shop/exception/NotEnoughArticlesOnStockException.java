package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown if there are not enough articles on stock for a given order.
 *
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.CONFLICT, reason = NotEnoughArticlesOnStockException.ERROR_MESSAGE)
public class NotEnoughArticlesOnStockException extends Exception {
    public static final String ERROR_MESSAGE = "Es sind nicht genügend Artikel auf Lager.";
}
