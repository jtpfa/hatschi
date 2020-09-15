package de.pcmr.shop.exception.keycloak;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = KeycloakUnknownErrorException.ERROR_MESSAGE)
public class KeycloakUnknownErrorException extends Exception {
    public static final String ERROR_MESSAGE = "Interner Server Error. Siehe Logs.";
}
