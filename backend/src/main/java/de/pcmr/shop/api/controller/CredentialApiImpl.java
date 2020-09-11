package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.CredentialChangeDTO;
import de.pcmr.shop.exception.PasswordException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class CredentialApiImpl implements CredentialApiI {
    @Value("${PCMR_AUTH_SERVER_URL}")
    private String authServerUrl;
    @Value("${PCMR_KEYCLOAK_REALM}")
    private String keycloakRealm;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void changePasswordOfCustomer(String authorizationHeader, @Valid CredentialChangeDTO credentialChangeDTO) throws MalformedURLException, PasswordException {
        URL keycloakUrl = new URL(authServerUrl);
        String logoutEndpointUrl = new URL(keycloakUrl, "/auth/realms/" + keycloakRealm + "/account/credentials/password").toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(AUTHORIZATION_HEADER_KEY, authorizationHeader);

        try {
            HttpEntity<CredentialChangeDTO> httpEntity = new HttpEntity<>(credentialChangeDTO, headers);
            restTemplate.exchange(logoutEndpointUrl, HttpMethod.POST, httpEntity, String.class);
        } catch (HttpClientErrorException ex) {
            handleHttpClientErrorException(ex);
        }
    }

    private void handleHttpClientErrorException(HttpClientErrorException ex) throws PasswordException {
        if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
            String errorMessage = ex.getResponseBodyAsString().split(":")[1];

            if (errorMessage.contains(NOT_MATCH_PASSWORD_MESSAGE)) {
                throw new PasswordException("Die Passwörter stimmen nicht überein.");
            } else if (errorMessage.contains(INVALID_PASSWORD_EXISTING_MESSAGE)) {
                throw new PasswordException("Das bisherige Passwort ist falsch.");
            } else if (errorMessage.contains(INVALID_PASSWORD_REGEX_PATTERN_MESSAGE)) {
                throw new PasswordException("Das Passwort entspricht nicht den Passwortregeln.");
            } else {
                throw ex;
            }
        }
    }
}
