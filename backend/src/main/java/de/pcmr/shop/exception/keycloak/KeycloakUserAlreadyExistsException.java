package de.pcmr.shop.exception.keycloak;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = KeycloakUserAlreadyExistsException.ERROR_MESSAGE)
public class KeycloakUserAlreadyExistsException extends Exception {
    public static final String ERROR_MESSAGE = "Es existiert bereits ein Nutzer mit dieser Email";

    public KeycloakUserAlreadyExistsException(Throwable cause) {
        super(ERROR_MESSAGE, cause);
    }
}
