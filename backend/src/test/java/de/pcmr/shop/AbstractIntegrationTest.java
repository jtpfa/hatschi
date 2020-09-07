package de.pcmr.shop;

import de.pcmr.shop.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;

@SpringBootTest
public abstract class AbstractIntegrationTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Value("${PCMR_AUTH_SERVER_URL}")
    private String keycloakUrl;
    @Value("${PCMR_KEYCLOAK_REALM}")
    private String keycloakRealm;
    @Value("${PCMR_RESOURCE}")
    private String keycloakClient;
    @Value("${PCMR_KEYCLOAK_REGISTRATION_USER}")
    private String keycloakRegistrationUser;
    @Value("${PCMR_KEYCLOAK_REGISTRATION_PASSWORD}")
    private String keycloakRegistrationPassword;

    private UsersResource usersResource;
    private boolean cleanupDatabaseBeforeEach = true;

    @PostConstruct
    void initKeycloak() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(keycloakRealm)
                .clientId(keycloakClient)
                .username(keycloakRegistrationUser)
                .password(keycloakRegistrationPassword)
                .build();

        RealmResource realmResource = keycloak.realm(keycloakRealm);
        usersResource = realmResource.users();
    }

    @BeforeEach
    void cleanUp() {
        if (cleanupDatabaseBeforeEach) {
            cleanUpDatabase();
            cleanUpKeycloak();
        }
    }

    void cleanUpDatabase() {
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();
        addressRepository.deleteAll();
        articleRepository.deleteAll();
        customerRepository.deleteAll();
    }

    void cleanUpKeycloak() {
        for (UserRepresentation user : usersResource.list()) {
            if (!user.getUsername().equals(keycloakRegistrationUser)) {
                usersResource.delete(user.getId());
            }
        }
    }

    protected UsersResource getUsersResource() {
        return usersResource;
    }

    public boolean isCleanupDatabaseBeforeEach() {
        return cleanupDatabaseBeforeEach;
    }

    public void setCleanupDatabaseBeforeEach(boolean cleanupDatabaseBeforeEach) {
        this.cleanupDatabaseBeforeEach = cleanupDatabaseBeforeEach;
    }
}
