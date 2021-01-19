package de.pcmr.shop.exception.keycloak;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown if a keycloak user already exists to the given email when creating a new one.
 *
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = KeycloakUserAlreadyExistsException.ERROR_MESSAGE)
public class KeycloakUserAlreadyExistsException extends Exception {
    public static final String ERROR_MESSAGE = "Es existiert bereits ein Nutzer mit dieser Email";

    public KeycloakUserAlreadyExistsException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
