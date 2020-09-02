package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.CredentialChangeDTO;
import de.pcmr.shop.exception.PasswordException;

import javax.validation.Valid;
import java.net.MalformedURLException;

public interface CredentialApiI {
    void changePasswordOfCustomer(String authorizationHeader, @Valid CredentialChangeDTO credentialChangeDTO) throws MalformedURLException, PasswordException;
}
