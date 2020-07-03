package de.pcmr.shop.persistence;

import de.pcmr.shop.builder.CustomerEntityBuilder;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerPersistenceTest {

    private final static String CUSTOMER_EMAIL = "test@test.com";
    private final static String CUSTOMER_FIRSTNAME = "John";
    private final static String CUSTOMER_LASTNAME = "Smith";

    private CustomerRepository customerRepository;
    private CustomerEntity customer;

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();

    @Autowired
    public CustomerPersistenceTest(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @BeforeEach
    public void cleanUpDatabase() {
        customerRepository.deleteAll();
    }

    @Test
    public void testSaveNewPerson() {
        given.aCustomerEntityWith(CUSTOMER_EMAIL, CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME);
        when.aCustomerIsSavedToTheDatabase();
        then.thereIsOneCustomerInTheDatabase();
        then.technicalAttributesShouldNotBeNull();
        then.passwordShouldBeNull();
        then.theAttributesOfTheCustomerAre(CUSTOMER_EMAIL, CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME);
    }

    class Given {
        public void aCustomerEntityWith(String email, String firstname, String lastname) {
            customer = CustomerEntityBuilder.aCustomerEntity()
                    .withEmail(email)
                    .withFirstName(firstname)
                    .withLastName(lastname)
                    .build();
        }
    }

    class When {
        public void aCustomerIsSavedToTheDatabase() {
            customerRepository.save(customer);
        }
    }

    class Then {
        public void thereIsOneCustomerInTheDatabase() {
            assertEquals(1, customerRepository.findAll().size());
        }

        public void theAttributesOfTheCustomerAre(String email, String firstname, String lastname) {
            CustomerEntity customer = customerRepository.findAll().get(0);
            assertEquals(email, customer.getEmail());
            assertEquals(firstname, customer.getFirstName());
            assertEquals(lastname, customer.getLastName());
        }

        public void passwordShouldBeNull() {
            CustomerEntity customer = customerRepository.findAll().get(0);
            assertNull(customer.getPassword());
        }

        public void technicalAttributesShouldNotBeNull() {
            CustomerEntity customer = customerRepository.findAll().get(0);
            assertNotNull(customer.getId());
            assertNotNull(customer.getCreated());
            //assertNotNull(customer.getCreatedBy());
            //assertNotNull(customer.getLastModifiedBy());
            assertNotNull(customer.getUpdated());
        }
    }
}
