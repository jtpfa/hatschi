package de.pcmr.shop.service;

import de.pcmr.shop.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
public abstract class AbstractServiceTest {
    private final Environment environment;

    private final String KEYCLOAK_URL;
    private final String KEYCLOAK_REALM;
    private final String KEYCLOAK_CLIENT;
    private final String KEYCLOAK_REGISTRATION_USER;
    private final String KEYCLOAK_REGISTRATION_PASSWORD;

    private final Keycloak keycloak;
    private final CustomerRepository customerRepository;
    private UsersResource usersResource;

    public AbstractServiceTest(Environment environment, CustomerRepository customerRepository) {
        this.environment = environment;
        KEYCLOAK_URL = this.environment.getProperty("PCMR_AUTH_SERVER_URL");
        KEYCLOAK_REALM = this.environment.getProperty("PCMR_KEYCLOAK_REALM");
        KEYCLOAK_CLIENT = this.environment.getProperty("PCMR_RESOURCE");
        KEYCLOAK_REGISTRATION_USER = this.environment.getProperty("PCMR_KEYCLOAK_REGISTRATION_USER");
        KEYCLOAK_REGISTRATION_PASSWORD = this.environment.getProperty("PCMR_KEYCLOAK_REGISTRATION_PASSWORD");

        this.customerRepository = customerRepository;

        keycloak = KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_URL)
                .realm(KEYCLOAK_REALM)
                .clientId(KEYCLOAK_CLIENT)
                .username(KEYCLOAK_REGISTRATION_USER)
                .password(KEYCLOAK_REGISTRATION_PASSWORD)
                .build();

        RealmResource realmResource = keycloak.realm(KEYCLOAK_REALM);
        usersResource = realmResource.users();
    }

    @BeforeEach
    public void cleanUp() {
        cleanUpDatabase();
        cleanUpKeycloak();
    }

    private void cleanUpDatabase() {
        customerRepository.deleteAll();
    }

    private void cleanUpKeycloak() {
        for (UserRepresentation user : usersResource.list()) {
            if (!user.getUsername().equals(KEYCLOAK_REGISTRATION_USER)) {
                usersResource.delete(user.getId());
            }
        }
    }

    protected UsersResource getUsersResource() {
        return usersResource;
    }
}
