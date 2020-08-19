package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.builder.CustomerEntityBuilder;
import de.pcmr.shop.domain.CustomerEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityCustomerDetailsDTOMapperUnitTest {
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
    void testMapCustomerEntityToCustomerDetailsDtoSuccess() {
        given.aCustomerEntityWith(CUSTOMER_EMAIL, CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME, CUSTOMER_PASSWORD);
        when.aCustomerEntityIsMappedToDto(customerEntity);
        then.theAttributesOfTheCustomerDetailsDtoAre(customerEntity);
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
    }

    class When {
        void aCustomerEntityIsMappedToDto(CustomerEntity customerEntity) {
            customerDetailsDTO = CustomerEntityCustomerDetailsDTOMapper.mapCustomerEntityToCustomerDetailsDto(customerEntity);
        }
    }

    class Then {
        void theAttributesOfTheCustomerDetailsDtoAre(CustomerEntity customerEntity) {
            assertEquals(customerEntity.getEmail(), customerDetailsDTO.getEmail());
            assertEquals(customerEntity.getFirstName(), customerDetailsDTO.getFirstName());
            assertEquals(customerEntity.getLastName(), customerDetailsDTO.getLastName());
        }
    }
}
