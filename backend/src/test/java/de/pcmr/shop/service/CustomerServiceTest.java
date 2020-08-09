package de.pcmr.shop.service;

import de.pcmr.shop.builder.CustomerEntityBuilder;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.repository.ArticleRepository;
import de.pcmr.shop.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintViolationException;
import java.security.Principal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest extends AbstractServiceTest {

    private final static String CUSTOMER_EMAIL_A = "test@userA.de";
    private final static String CUSTOMER_FIRSTNAME_A = "TestA";
    private final static String CUSTOMER_LASTNAME_A = "UserA";
    private final static String CUSTOMER_PASSWORD_A = "MyP@ssw0rdA";

    private final static String CUSTOMER_EMAIL_B = "test@userB.de";
    private final static String CUSTOMER_FIRSTNAME_B = "TestB";
    private final static String CUSTOMER_LASTNAME_B = "UserB";
    private final static String CUSTOMER_PASSWORD_B = "MyP@ssw0rdB";

    private final static String CUSTOMER_INVALID_EMAIL = "test@";
    private final static String CUSTOMER_INVALID_FIRSTNAME = "";

    private final RegistrationServiceI registrationService;
    private final CustomerServiceI customerService;

    private final CustomerRepository customerRepository;

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    private CustomerEntity customerEntity;
    private CustomerEntity customerEntityA;
    private CustomerEntity customerEntityB;
    private CustomerEntity updatedCustomerEntity;

    private final UsersResource usersResource;

    @Autowired
    CustomerServiceTest(RegistrationServiceI registrationService, CustomerServiceI customerService, CustomerRepository customerRepository, Environment environment, ArticleRepository articleRepository) {
        super(environment, customerRepository, articleRepository);
        this.registrationService = registrationService;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.usersResource = super.getUsersResource();
    }

    @Test
    void testGetCurrentCustomerWithOneCustomerSuccess() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NoCustomerFoundException {
        given.aCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        when.aCustomerIsRegistered(customerEntity);
        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        then.theAttributesOfTheCurrentCustomerAre(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A);
    }

    @Test
    void testGetCurrentCustomerWithMultipleCustomersSuccess() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NoCustomerFoundException {
        given.twoCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A, CUSTOMER_EMAIL_B, CUSTOMER_FIRSTNAME_B, CUSTOMER_LASTNAME_B, CUSTOMER_PASSWORD_B);
        when.aCustomerIsRegistered(customerEntityB);
        when.aCustomerIsRegistered(customerEntityA);
        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        then.theAttributesOfTheCurrentCustomerAre(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A);
    }

    @Test
    void testGetCurrentCustomerWithOneCustomerFailed() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        given.aCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        when.aCustomerIsRegistered(customerEntity);
        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_B, CUSTOMER_PASSWORD_B);
        assertThrows(NoCustomerFoundException.class, () -> then.theAttributesOfTheCurrentCustomerAre(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A));
    }

    @Test
    void testUpdateCurrentCustomer() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NoCustomerFoundException {
        given.aCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        given.aUpdatedCustomerEntityWith(CUSTOMER_EMAIL_B, CUSTOMER_FIRSTNAME_B, CUSTOMER_LASTNAME_B, CUSTOMER_PASSWORD_B);
        when.aCustomerIsRegistered(customerEntity);
        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        when.theCurrentCustomerIsUpdatedTo(updatedCustomerEntity);
        then.theAttributesOfTheCustomerAre(CUSTOMER_EMAIL_B, CUSTOMER_FIRSTNAME_B, CUSTOMER_LASTNAME_B);
        then.numberOfKeycloakUsersAre(2);
        then.theEmailAndUsernameOfTheKeycloakUserIsUpdatedTo(CUSTOMER_EMAIL_B);
    }

    @Test
    void testUpdateCurrentCustomerSomeAttributes() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NoCustomerFoundException {
        given.aCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        given.aUpdatedCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_B, CUSTOMER_LASTNAME_B, CUSTOMER_PASSWORD_B);
        when.aCustomerIsRegistered(customerEntity);
        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        when.theCurrentCustomerIsUpdatedTo(updatedCustomerEntity);
        then.theAttributesOfTheCustomerAre(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_B, CUSTOMER_LASTNAME_B);
        then.numberOfKeycloakUsersAre(2);
    }

    @Test
    void testUpdateFailedDueToInvalidEmail() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        given.aCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        given.aUpdatedCustomerEntityWith(CUSTOMER_INVALID_EMAIL, CUSTOMER_FIRSTNAME_B, CUSTOMER_LASTNAME_B, CUSTOMER_PASSWORD_B);
        when.aCustomerIsRegistered(customerEntity);
        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        assertThrows(ConstraintViolationException.class, () -> when.theCurrentCustomerIsUpdatedTo(updatedCustomerEntity));
    }

    @Test
    void testUpdateFailedDueToInvalidFirstname() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        given.aCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        given.aUpdatedCustomerEntityWith(CUSTOMER_INVALID_FIRSTNAME, CUSTOMER_FIRSTNAME_B, CUSTOMER_LASTNAME_B, CUSTOMER_PASSWORD_B);
        when.aCustomerIsRegistered(customerEntity);
        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        assertThrows(ConstraintViolationException.class, () -> when.theCurrentCustomerIsUpdatedTo(updatedCustomerEntity));
    }

    @Test
    void testUpdateFailedDueToUsedEmail() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        given.twoCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A, CUSTOMER_EMAIL_B, CUSTOMER_FIRSTNAME_B, CUSTOMER_LASTNAME_B, CUSTOMER_PASSWORD_B);
        given.aUpdatedCustomerEntityWith(CUSTOMER_EMAIL_B, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        when.aCustomerIsRegistered(customerEntityA);
        when.aCustomerIsRegistered(customerEntityB);
        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        assertThrows(CustomerAlreadyExistsException.class, () -> when.theCurrentCustomerIsUpdatedTo(updatedCustomerEntity));
    }

    class Given {
        void aCustomerEntityWith(String email, String firstname, String lastname, String password) {
            customerEntity = CustomerEntityBuilder.aCustomerEntity()
                    .withEmail(email)
                    .withFirstName(firstname)
                    .withLastName(lastname)
                    .withPassword(password)
                    .build();
        }

        void twoCustomerEntityWith(String emailA, String firstnameA, String lastnameA, String passwordA, String emailB, String firstnameB, String lastnameB, String passwordB) {
            customerEntityB = CustomerEntityBuilder.aCustomerEntity()
                    .withEmail(emailB)
                    .withFirstName(firstnameB)
                    .withLastName(lastnameB)
                    .withPassword(passwordB)
                    .build();

            customerEntityA = CustomerEntityBuilder.aCustomerEntity()
                    .withEmail(emailA)
                    .withFirstName(firstnameA)
                    .withLastName(lastnameA)
                    .withPassword(passwordA)
                    .build();
        }

        void aUpdatedCustomerEntityWith(String email, String firstname, String lastname, String password) {
            updatedCustomerEntity = CustomerEntityBuilder.aCustomerEntity()
                    .withEmail(email)
                    .withFirstName(firstname)
                    .withLastName(lastname)
                    .withPassword(password)
                    .build();
        }
    }

    class When {
        void aCustomerIsRegistered(CustomerEntity customer) throws KeycloakEndpointNotFoundException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, KeycloakUnknownErrorException {
            registrationService.registerCustomer(customer);
        }

        void aRegisteredCustomerIsAuthenticated(String email, String password) {
            Authentication auth = new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
        }

        void theCurrentCustomerIsUpdatedTo(CustomerEntity updatedCustomerEntity) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
            customerService.updateCurrentCustomer(updatedCustomerEntity, SecurityContextHolder.getContext().getAuthentication());
        }
    }

    class Then {
        void theAttributesOfTheCurrentCustomerAre(String email, String firstname, String lastname) throws NoCustomerFoundException {
            Principal principal = SecurityContextHolder.getContext().getAuthentication();
            CustomerEntity customer = customerService.getCurrentCustomer(principal);

            assertEquals(email, customer.getEmail());
            assertEquals(firstname, customer.getFirstName());
            assertEquals(lastname, customer.getLastName());
        }

        void theAttributesOfTheCustomerAre(String email, String firstname, String lastname) {
            CustomerEntity customerEntity = customerRepository.findAll().get(0);

            assertEquals(email, customerEntity.getEmail());
            assertEquals(firstname, customerEntity.getFirstName());
            assertEquals(lastname, customerEntity.getLastName());
        }

        void theEmailAndUsernameOfTheKeycloakUserIsUpdatedTo(String email) {
            email = email.toLowerCase();
            UserRepresentation userRepresentation = usersResource.search(email).get(0);

            assertNotNull(userRepresentation);
            assertEquals(email, userRepresentation.getUsername());
            assertEquals(email, userRepresentation.getEmail());
        }

        void numberOfKeycloakUsersAre(int expected) {
            assertEquals(expected, usersResource.list().size());
        }
    }
}
