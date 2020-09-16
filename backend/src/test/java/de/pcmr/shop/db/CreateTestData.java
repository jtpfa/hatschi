package de.pcmr.shop.db;

import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateTestData {
    @Autowired DBPreparer dbPreparer;

    @Test
    @Disabled
    void createTestData() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        dbPreparer.prepareTestDatabase();
    }
}
