package de.pcmr.shop.exception.keycloak;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception is thrown if no keycloak endpoint has been found (invalid configuration).
 *
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = KeycloakEndpointNotFoundException.ERROR_MESSAGE)
public class KeycloakEndpointNotFoundException extends Exception {
    public static final String ERROR_MESSAGE = "Interner Server Error. Siehe Logs";
    public static final String INTERNAL_ERROR_MESSAGE = "Keycloak-Endpunkt konnte nicht gefunden werden";

    public KeycloakEndpointNotFoundException(Throwable cause) {
        super(INTERNAL_ERROR_MESSAGE, cause);
    }
}
