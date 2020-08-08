package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Service
@Validated
public class CustomerServiceImpl implements CustomerServiceI {
    private final CustomerRepository customerRepository;
    private final KeycloakServiceI keycloakService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, KeycloakServiceI keycloakService) {
        this.customerRepository = customerRepository;
        this.keycloakService = keycloakService;
    }

    @Override
    public CustomerEntity getCurrentCustomer(Principal principal) throws NoCustomerFoundException {
        return getCurrentCustomerIfExists(principal);
    }

    @Override
    public void updateCurrentCustomer(@Valid CustomerEntity customerEntity, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException {
        CustomerEntity currentCustomerEntity = getCurrentCustomerIfExists(principal);

        checkIfUserWithSameEmailExists(customerEntity, principal.getName());
        keycloakService.updateKeycloakUser(customerEntity, principal.getName());

        currentCustomerEntity.setEmail(customerEntity.getEmail());
        currentCustomerEntity.setFirstName(customerEntity.getFirstName());
        currentCustomerEntity.setLastName(customerEntity.getLastName());
        customerRepository.save(currentCustomerEntity);
    }

    private CustomerEntity getCurrentCustomerIfExists(Principal principal) throws NoCustomerFoundException {
        Optional<CustomerEntity> customerEntity = customerRepository.findByEmail(principal.getName());
        if (customerEntity.isPresent()) {
            return customerEntity.get();
        } else {
            throw new NoCustomerFoundException();
        }
    }

    private void checkIfUserWithSameEmailExists(CustomerEntity newCustomerEntity, String currentEmail) throws CustomerAlreadyExistsException {
        if (!newCustomerEntity.getEmail().equalsIgnoreCase(currentEmail)
        && customerRepository.findByEmail(newCustomerEntity.getEmail()).isPresent()) {
            throw new CustomerAlreadyExistsException();
        }
    }
}
