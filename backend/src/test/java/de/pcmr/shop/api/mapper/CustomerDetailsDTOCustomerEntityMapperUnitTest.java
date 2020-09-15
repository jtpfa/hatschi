package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.domain.CustomerEntity;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Fynn Lohse
 */

class CustomerDetailsDTOCustomerEntityMapperUnitTest {
    private final static String CUSTOMER_EMAIL = "test@test.com";
    private final static String CUSTOMER_FIRSTNAME = "John";
    private final static String CUSTOMER_FIRSTNAME_HTML = "<script>John</script>";
    private final static String CUSTOMER_LASTNAME = "Smith";

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    CustomerDetailsDTO customerDetailsDTO;
    CustomerEntity customerEntity;

    @Test
    void testMapCustomerDetailsDTOToCustomerEntityTestSuccess() {
        given.aCustomerDetailsDtoWith(CUSTOMER_EMAIL, CUSTOMER_FIRSTNAME, CUSTOMER_LASTNAME);
        when.aCustomerDetailsDtoIsMappedToEntity(customerDetailsDTO);
        then.theAttributesOfTheCustomerAreEqualTo(customerDetailsDTO);
    }

    @Test
    void testMapCustomerDetailsDTOToCustomerEntityTestFailHtml() {
        given.aCustomerDetailsDtoWith(CUSTOMER_EMAIL, CUSTOMER_FIRSTNAME_HTML, CUSTOMER_LASTNAME);
        assertThrows(ValidationException.class, () -> when.aCustomerDetailsDtoIsMappedToEntity(customerDetailsDTO));
    }

    class Given {
        void aCustomerDetailsDtoWith(String email, String firstname, String lastname) {
            customerDetailsDTO = new CustomerDetailsDTO();
            customerDetailsDTO.setEmail(email);
            customerDetailsDTO.setFirstName(firstname);
            customerDetailsDTO.setLastName(lastname);
        }
    }

    class When {
        void aCustomerDetailsDtoIsMappedToEntity(CustomerDetailsDTO customerDetailsDTO) {
            customerEntity = CustomerMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsDTO);
        }
    }

    class Then {
        void theAttributesOfTheCustomerAreEqualTo(CustomerDetailsDTO customerDetailsDTO) {
            assertEquals(customerDetailsDTO.getEmail(), customerEntity.getEmail());
            assertEquals(customerDetailsDTO.getFirstName(), customerEntity.getFirstName());
            assertEquals(customerDetailsDTO.getLastName(), customerEntity.getLastName());
        }
    }
}
