package de.pcmr.shop.exception.keycloak;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Fynn Lohse
 */

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = KeycloakUserIsNotAuthorizedException.ERROR_MESSAGE)
public class KeycloakUserIsNotAuthorizedException extends Exception {
    public static final String ERROR_MESSAGE = "Interner Server Error. Siehe Logs";
    public static final String INTERNAL_ERROR_MESSAGE = "Zugriff auf Keycloak-Endpunkt verweigert";

    public KeycloakUserIsNotAuthorizedException(Throwable cause) {
        super(INTERNAL_ERROR_MESSAGE, cause);
    }
}
