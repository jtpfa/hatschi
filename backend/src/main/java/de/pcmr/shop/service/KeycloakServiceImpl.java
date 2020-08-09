package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
public class KeycloakServiceImpl implements KeycloakServiceI {

    @Value("${PCMR_AUTH_SERVER_URL}")
    private String authServerUrl;
    @Value("${PCMR_KEYCLOAK_REALM}")
    private String keycloakRealm;
    @Value("${PCMR_RESOURCE}")
    private String keycloakClient;
    @Value("${PCMR_KEYCLOAK_REGISTRATION_USER}")
    private String keycloakRegistrationUser;
    @Value("${PCMR_KEYCLOAK_REGISTRATION_PASSWORD}")
    private String keycloakRegistrationPassword;

    private Keycloak keycloak;

    @PostConstruct
    public void initKeycloak() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(keycloakRealm)
                .clientId(keycloakClient)
                .username(keycloakRegistrationUser)
                .password(keycloakRegistrationPassword)
                .build();
    }

    @Override
    public void createKeycloakUser(CustomerEntity customerEntity) throws KeycloakUserIsNotAuthorizedException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUnknownErrorException {
        try {
            UserRepresentation user = createKeycloakUserFromCustomer(customerEntity);

            RealmResource realmResource = keycloak.realm(keycloakRealm);
            UsersResource usersResource = realmResource.users();

            Response response = usersResource.create(user);
            String userId = CreatedResponseUtil.getCreatedId(response);

            CredentialRepresentation passwordCredentials = createCredentialsFromCustomer(customerEntity);

            UserResource userResource = usersResource.get(userId);
            userResource.resetPassword(passwordCredentials);
        } catch (WebApplicationException ex) {
            handleWebApplicationException(ex);
        }
    }

    @Override
    public void updateKeycloakUser(CustomerEntity customerEntity, String currentUsername) throws KeycloakEndpointNotFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException {
        try {
            RealmResource realmResource = keycloak.realm(keycloakRealm);
            UsersResource usersResource = realmResource.users();

            List<UserRepresentation> keycloakUserList = usersResource.search(currentUsername);

            if (keycloakUserList.size() != 1) {
                throw new IllegalStateException();
            }

            UserRepresentation userRepresentation = keycloakUserList.get(0);
            userRepresentation.setUsername(customerEntity.getEmail());
            userRepresentation.setEmail(customerEntity.getEmail());
            userRepresentation.setFirstName(customerEntity.getFirstName());
            userRepresentation.setLastName(customerEntity.getLastName());

            UserResource userResource = usersResource.get(userRepresentation.getId());
            userResource.update(userRepresentation);
        } catch (ClientErrorException ex) {
            handleWebApplicationException(ex);
        }
    }

    private void handleWebApplicationException(WebApplicationException ex) throws KeycloakUserIsNotAuthorizedException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUnknownErrorException {
        switch (ex.getResponse().getStatus()) {
            case 401:
                throw new KeycloakUserIsNotAuthorizedException(ex);
            case 404:
                throw new KeycloakEndpointNotFoundException(ex);
            case 409:
                throw new KeycloakUserAlreadyExistsException(ex);
            case 400:
                throw new KeycloakUnknownErrorException();
            default:
                throw ex;
        }
    }

    private UserRepresentation createKeycloakUserFromCustomer(CustomerEntity customerEntity) {
        UserRepresentation user = new UserRepresentation();
        user.setEmail(customerEntity.getEmail());
        user.setFirstName(customerEntity.getFirstName());
        user.setLastName(customerEntity.getLastName());
        user.setUsername(customerEntity.getEmail());
        user.setAttributes(Collections.singletonMap("origin", Collections.singletonList("pcmr_application")));
        user.setEnabled(true);

        return user;
    }

    private CredentialRepresentation createCredentialsFromCustomer(CustomerEntity customerEntity) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(customerEntity.getPassword());

        return passwordCredentials;
    }
}
