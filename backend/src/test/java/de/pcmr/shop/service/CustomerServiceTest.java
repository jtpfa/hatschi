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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerServiceTest extends AbstractServiceTest {

    private final static String CUSTOMER_EMAIL_A = "test@userA.de";
    private final static String CUSTOMER_FIRSTNAME_A = "TestA";
    private final static String CUSTOMER_LASTNAME_A = "UserA";
    private final static String CUSTOMER_PASSWORD_A = "MyP@ssw0rdA";

    private final static String CUSTOMER_EMAIL_B = "test@userB.de";
    private final static String CUSTOMER_FIRSTNAME_B = "TestB";
    private final static String CUSTOMER_LASTNAME_B = "UserB";
    private final static String CUSTOMER_PASSWORD_B = "MyP@ssw0rdB";

    private final RegistrationServiceI registrationService;
    private final CustomerServiceI customerService;

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    private CustomerEntity customerEntity;
    private CustomerEntity customerEntityA;
    private CustomerEntity customerEntityB;

    @Autowired
    public CustomerServiceTest(RegistrationServiceI registrationService, CustomerServiceI customerService, CustomerRepository customerRepository, Environment environment, ArticleRepository articleRepository) {
        super(environment, customerRepository, articleRepository);
        this.registrationService = registrationService;
        this.customerService = customerService;
    }

    @Test
    public void testGetCurrentCustomerWithOneCustomerSuccess() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NoCustomerFoundException {
        given.aCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        when.aCustomerIsRegistered(customerEntity);
        when.aRegistredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        then.theAttributesOfTheCurrentCustomerAre(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A);
    }

    @Test
    public void testGetCurrentCustomerWithMultipleCustomersSuccess() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NoCustomerFoundException {
        given.twoCustomerEntityWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A, CUSTOMER_EMAIL_B, CUSTOMER_FIRSTNAME_B, CUSTOMER_LASTNAME_B, CUSTOMER_PASSWORD_B);
        when.aCustomerIsRegistered(customerEntityB);
        when.aCustomerIsRegistered(customerEntityA);
        when.aRegistredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        then.theAttributesOfTheCurrentCustomerAre(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A);
    }

    class Given {
        public void aCustomerEntityWith(String email, String firstname, String lastname, String password) {
            customerEntity = CustomerEntityBuilder.aCustomerEntity()
                    .withEmail(email)
                    .withFirstName(firstname)
                    .withLastName(lastname)
                    .withPassword(password)
                    .build();
        }

        public void twoCustomerEntityWith(String emailA, String firstnameA, String lastnameA, String passwordA, String emailB, String firstnameB, String lastnameB, String passwordB) {
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
    }

    class When {
        public void aCustomerIsRegistered(CustomerEntity customer) throws KeycloakEndpointNotFoundException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, KeycloakUnknownErrorException {
            registrationService.registerCustomer(customer);
        }

        public void aRegistredCustomerIsAuthenticated(String email, String password) {
            Authentication auth = new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
        }
    }

    class Then {
        public void theAttributesOfTheCurrentCustomerAre(String email, String firstname, String lastname) throws NoCustomerFoundException {
            Principal principal = SecurityContextHolder.getContext().getAuthentication();
            CustomerEntity customer = customerService.getCurrentCustomer(principal);

            assertEquals(email, customer.getEmail());
            assertEquals(firstname, customer.getFirstName());
            assertEquals(lastname, customer.getLastName());
        }
    }
}
