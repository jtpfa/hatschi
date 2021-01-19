package de.pcmr.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown if there were duplicate order items in an order entity (same article id).
 *
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = DuplicateOrderItemsException.ERROR_MESSAGE)
public class DuplicateOrderItemsException extends Exception {
    public static final String ERROR_MESSAGE = "In der Bestellung existieren mehrere Einträge für das gleiche Produkt";
}
