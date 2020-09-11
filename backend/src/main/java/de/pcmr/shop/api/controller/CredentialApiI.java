package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.CredentialChangeDTO;
import de.pcmr.shop.exception.PasswordException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.MalformedURLException;

@RequestMapping("/api")
public interface CredentialApiI {
    String PASSWORD_CUSTOMER_URI = "/customer/password";
    String AUTHORIZATION_HEADER_KEY = "Authorization";

    String NOT_MATCH_PASSWORD_MESSAGE = "notMatchPasswordMessage";
    String INVALID_PASSWORD_EXISTING_MESSAGE = "invalidPasswordExistingMessage";
    String INVALID_PASSWORD_REGEX_PATTERN_MESSAGE = "invalidPasswordRegexPatternMessage";

    @PostMapping(PASSWORD_CUSTOMER_URI)
    void changePasswordOfCustomer(@RequestHeader(AUTHORIZATION_HEADER_KEY) String authorizationHeader, @Valid @RequestBody CredentialChangeDTO credentialChangeDTO) throws MalformedURLException, PasswordException;
}
