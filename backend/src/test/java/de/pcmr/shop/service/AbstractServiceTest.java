package de.pcmr.shop.service;

import de.pcmr.shop.repository.ArticleRepository;
import de.pcmr.shop.repository.CustomerRepository;
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

    private final String KEYCLOAK_REGISTRATION_USER;

    private final CustomerRepository customerRepository;
    private final ArticleRepository articleRepository;
    private final UsersResource usersResource;

    public AbstractServiceTest(Environment environment, CustomerRepository customerRepository, ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        String KEYCLOAK_URL = environment.getProperty("PCMR_AUTH_SERVER_URL");
        String KEYCLOAK_REALM = environment.getProperty("PCMR_KEYCLOAK_REALM");
        String KEYCLOAK_CLIENT = environment.getProperty("PCMR_RESOURCE");
        KEYCLOAK_REGISTRATION_USER = environment.getProperty("PCMR_KEYCLOAK_REGISTRATION_USER");
        String KEYCLOAK_REGISTRATION_PASSWORD = environment.getProperty("PCMR_KEYCLOAK_REGISTRATION_PASSWORD");

        this.customerRepository = customerRepository;

        Keycloak keycloak = KeycloakBuilder.builder()
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
        articleRepository.deleteAll();
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
