package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.repository.CustomerRepository;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
@Validated
public class RegistrationServiceImpl implements RegistrationServiceI {

    private final String keycloakRealm;

    private final CustomerRepository customerRepository;

    private final Keycloak keycloak;

    @Autowired
    public RegistrationServiceImpl(Environment env, CustomerRepository customerRepository) {
        String authServerUrl = env.getProperty("PCMR_AUTH_SERVER_URL");
        keycloakRealm = env.getProperty("PCMR_KEYCLOAK_REALM");
        String keycloakClient = env.getProperty("PCMR_RESOURCE");
        String keycloakRegistrationUser = env.getProperty("PCMR_KEYCLOAK_REGISTRATION_USER");
        String keycloakRegistrationPassword = env.getProperty("PCMR_KEYCLOAK_REGISTRATION_PASSWORD");
        this.customerRepository = customerRepository;

        keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(keycloakRealm)
                .clientId(keycloakClient)
                .username(keycloakRegistrationUser)
                .password(keycloakRegistrationPassword)
                .build();
    }

    @Override
    public void registerCustomer(@Valid CustomerEntity customerEntity) throws KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUnknownErrorException {
        try {
            UserRepresentation user = createKeycloakUserFromCustomer(customerEntity);

            RealmResource realmResource = keycloak.realm(keycloakRealm);
            UsersResource usersResource = realmResource.users();

            Response response = usersResource.create(user);
            String userId = CreatedResponseUtil.getCreatedId(response);

            CredentialRepresentation passwordCredentials = createCredentialsFromCustomer(customerEntity);

            UserResource userResource = usersResource.get(userId);
            userResource.resetPassword(passwordCredentials);

            saveCustomerEntityIfNotExists(customerEntity);
        } catch (WebApplicationException ex) {
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
                ex.printStackTrace();
        }
    }

    private void saveCustomerEntityIfNotExists(CustomerEntity customerEntity) throws CustomerAlreadyExistsException {
        if (customerRepository.findByEmail(customerEntity.getEmail()).isEmpty()) {
            customerRepository.save(customerEntity);
        } else {
            throw new CustomerAlreadyExistsException();
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
