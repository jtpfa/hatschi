package de.pcmr.shop.service;

import de.pcmr.shop.builder.CustomerEntityBuilder;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegistrationServiceTest {
    private RegistrationServiceI registrationService;

    @Autowired
    public RegistrationServiceTest(RegistrationServiceI registrationService) {
        this.registrationService = registrationService;
    }

    @Test
    public void testUserRegistration() throws KeycloakEndpointNotFoundException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException {
        CustomerEntity customer = CustomerEntityBuilder.aCustomerEntity()
                .withFirstName("Peter")
                .withLastName("Meier")
                .withEmail("peter@meier.com")
                .withId(123)
                .withPassword("p@ssw0rd".toCharArray())
                .build();
        registrationService.registerCustomer(customer);
    }
}
