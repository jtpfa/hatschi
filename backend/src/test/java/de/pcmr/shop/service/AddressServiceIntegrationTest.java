package de.pcmr.shop.service;

import de.pcmr.shop.builder.AddressEntityBuilder;
import de.pcmr.shop.db.DBPreparer;
import de.pcmr.shop.domain.AddressEntity;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Fynn Lohse
 */

@SpringBootTest
public class AddressServiceIntegrationTest {

    final Given given = new Given();
    final When when = new When();
    final Then then = new Then();

    private Map<String, AddressEntity> addresses;
    private List<AddressEntity> executionResult;

    private CustomerEntity customerA;
    private AddressEntity editedAddress;
    private AddressEntity newAddress;

    @Autowired
    private AddressServiceI addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DBPreparer dbPreparer;

    @BeforeEach
    void setupDatabase() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        dbPreparer.prepareTestDatabase();
        addresses = dbPreparer.getAddresses();
    }

    @Test
    void testGetAddresses() throws NoCustomerFoundException {
        given.aCustomerEntityWithAddresses();
        when.aRegisteredCustomerIsAuthenticated(customerA.getEmail(), "password1!");
        when.getAddressesIsCalled();
        then.resultListSizeIs(2, executionResult);
    }

    @Test
    void testEditAddress() throws AddressDoesNotBelongToUserException, NoCustomerFoundException, NoAddressFoundException {
        given.aCustomerEntityWithAddresses();
        given.anEditedAddress(addresses.get("CA1").getId());
        when.aRegisteredCustomerIsAuthenticated(customerA.getEmail(), "password1!");
        when.anAddressIsEdited(editedAddress);
        then.theEditedAddressEquals(editedAddress);
    }

    @Test
    void  testCreateAddress() throws NoCustomerFoundException {
        given.aCustomerEntityWithAddresses();
        given.aNewAddress();
        when.aRegisteredCustomerIsAuthenticated(customerA.getEmail(), "password1!");
        when.aNewAddressIsCreated(newAddress);
        then.theAuthenticatedCustomerAddressListSizeIs(3);
    }

    @Test
    void testEditAddressNotFound() {
        given.aCustomerEntityWithAddresses();
        given.anEditedAddress(0L);
        when.aRegisteredCustomerIsAuthenticated(customerA.getEmail(), "password1!");
        assertThrows(NoAddressFoundException.class, () -> when.anAddressIsEdited(editedAddress));
    }

    @Test
    void testEditAddressUnauthorized() {
        given.aCustomerEntityWithAddresses();
        given.anEditedAddress(addresses.get("CA1").getId());
        when.aRegisteredCustomerIsAuthenticated(dbPreparer.getCustomers().get("b@customer").getEmail(), "blab");
        assertThrows(AddressDoesNotBelongToUserException.class, () -> when.anAddressIsEdited(editedAddress));
    }

    @Test
    void testDeleteAddress() throws AddressDoesNotBelongToUserException, NoCustomerFoundException, NoAddressFoundException {
        given.aCustomerEntityWithAddresses();
        when.aRegisteredCustomerIsAuthenticated(customerA.getEmail(), "password1!");
        when.anAddressIsDeleted(addresses.get("CA1").getId());
        then.theAddressWithIdStillExists(addresses.get("CA1").getId());
        then.theAuthenticatedCustomerAddressListSizeIs(1);
        then.theAddressWithIdIsDisabled(addresses.get("CA1").getId());
    }

    @Test
    void testDeleteAddressNotFound() {
        given.aCustomerEntityWithAddresses();
        when.aRegisteredCustomerIsAuthenticated(customerA.getEmail(), "password1!");
        assertThrows(NoAddressFoundException.class, () -> when.anAddressIsDeleted(0L));
    }

    @Test
    void testDeleteAddressUnauthorized() {
        given.aCustomerEntityWithAddresses();
        when.aRegisteredCustomerIsAuthenticated(dbPreparer.getCustomers().get("b@customer").getEmail(), "blab");
        assertThrows(AddressDoesNotBelongToUserException.class, () -> when.anAddressIsDeleted(addresses.get("CA1").getId()));
    }

    @Test
    void testDeleteAddressInactive() {
        given.aCustomerEntityWithAddresses();
        when.aRegisteredCustomerIsAuthenticated(customerA.getEmail(), "blab");
        assertThrows(NoAddressFoundException.class, () -> when.anAddressIsDeleted(addresses.get("CA2").getId()));
    }

    class Given {
        void aCustomerEntityWithAddresses() {
            customerA = dbPreparer.getCustomers().get("a@customer");
        }

        void anEditedAddress(Long id) {
             editedAddress = AddressEntityBuilder.anAddressEntity()
                     .withId(id)
                     .withAdditionalAddress("A")
                     .withAddress("Teststraße 1")
                     .withActive(true)
                     .withCity("Berlin")
                     .withCountry("Deutschland")
                     .withFirstName("Peter")
                     .withLastName("Schmidt")
                     .withZip("12345")
                     .withCustomer(dbPreparer.getCustomers().get("a@customer"))
                     .build();
        }

        void aNewAddress() {
            newAddress = AddressEntityBuilder.anAddressEntity()
                    .withAdditionalAddress("A")
                    .withAddress("Teststraße 1")
                    .withActive(true)
                    .withCity("Berlin")
                    .withCountry("Deutschland")
                    .withFirstName("Peter")
                    .withLastName("Schmidt")
                    .withZip("12345")
                    .build();
        }
    }

    class When {
        void aRegisteredCustomerIsAuthenticated(String email, String password) {
            Authentication auth = new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
        }

        void getAddressesIsCalled() throws NoCustomerFoundException {
            executionResult = addressService.getAddressesOfCustomer(SecurityContextHolder.getContext().getAuthentication());
        }

        void anAddressIsEdited(AddressEntity addressEntity) throws AddressDoesNotBelongToUserException, NoCustomerFoundException, NoAddressFoundException {
            addressService.editAddressIfAuthenticated(addressEntity, SecurityContextHolder.getContext().getAuthentication());
        }

        void anAddressIsDeleted(Long id) throws AddressDoesNotBelongToUserException, NoCustomerFoundException, NoAddressFoundException {
            addressService.deleteAddress(id, SecurityContextHolder.getContext().getAuthentication());
        }

        void aNewAddressIsCreated(AddressEntity addressEntity) throws NoCustomerFoundException {
            addressService.createAddress(addressEntity, SecurityContextHolder.getContext().getAuthentication());
        }
    }

    class Then {
        <T> void resultListSizeIs(int size, List<T> list) {
            assertEquals(size, list.size());
        }

        void theEditedAddressEquals(AddressEntity addressEntity) {
            AddressEntity editedAddress = addressRepository.findById(addressEntity.getId()).orElseThrow();
            theAddressEquals(addressEntity, editedAddress);
        }

        void theAddressEquals(AddressEntity expected, AddressEntity actual) {
            assertEquals(expected.getAdditionalAddress(), actual.getAdditionalAddress());
            assertEquals(expected.getAddress(), actual.getAddress());
            assertEquals(expected.getZip(), actual.getZip());
            assertEquals(expected.getFirstName(), actual.getFirstName());
            assertEquals(expected.getLastName(), actual.getLastName());
            assertEquals(expected.getCity(), actual.getCity());
            assertEquals(expected.getCountry(), actual.getCountry());
            assertEquals(expected.getCustomer().getId(), actual.getCustomer().getId());
        }

        void theAddressWithIdStillExists(Long id) {
            assertTrue(addressRepository.existsById(id));
        }

        void theAddressWithIdIsDisabled(Long id) {
            AddressEntity addressEntity = addressRepository.findById(id).orElseThrow();
            assertFalse(addressEntity.isActive());
        }

        void theAuthenticatedCustomerAddressListSizeIs(int expectedSize) throws NoCustomerFoundException {
            List<AddressEntity> addresses = addressService.getAddressesOfCustomer(SecurityContextHolder.getContext().getAuthentication());
            assertEquals(addresses.size(), expectedSize);
        }
    }
}
