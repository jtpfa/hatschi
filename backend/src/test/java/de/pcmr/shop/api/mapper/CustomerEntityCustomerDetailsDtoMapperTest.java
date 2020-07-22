package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.builder.CustomerEntityBuilder;
import de.pcmr.shop.domain.CustomerEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerEntityCustomerDetailsDtoMapperTest {
    private final static String CUSTOMER_EMAIL = "test@test.com";
    private final static String CUSTOMER_FIRSTNAME = "John";
    private final static String CUSTOMER_LASTNAME = "Smith";
    private final static String CUSTOMER_PASSWORD = "MyS3cr3tP@ssw0rd";

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();

    CustomerEntity customerEntity;
    CustomerDetailsDTO customerDetailsDTO;

    @Test
    public void testMapCustomerEntityToCustomerDetailsDtoSuccess() {
        given.aCustomerEntityWith(CUSTOMER_EMAIL, CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME, CUSTOMER_PASSWORD);
        when.aCustomerEntityIsMappedToDto(customerEntity);
        then.theAttributesOfTheCustomerDetailsDtoAre(customerEntity);
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
    }

    class When {
        public void aCustomerEntityIsMappedToDto(CustomerEntity customerEntity) {
            customerDetailsDTO = CustomerEntityCustomerDetailsDtoMapper.mapCustomerEntityToCustomerDetailsDto(customerEntity);
        }
    }

    class Then {
        public void theAttributesOfTheCustomerDetailsDtoAre(CustomerEntity customerEntity) {
            assertEquals(customerEntity.getEmail(), customerDetailsDTO.getEmail());
            assertEquals(customerEntity.getFirstName(), customerDetailsDTO.getFirstName());
            assertEquals(customerEntity.getLastName(), customerDetailsDTO.getLastName());
        }
    }
}
