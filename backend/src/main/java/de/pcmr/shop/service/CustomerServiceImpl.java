package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.domain.CustomerRoleEnum;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotAuthorizedException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(rollbackFor = Exception.class)
    public void updateCurrentCustomer(@Valid CustomerEntity customerEntity, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException {
        CustomerEntity currentCustomerEntity = getCurrentCustomerIfExists(principal);

        checkIfUserWithSameEmailExists(customerEntity, principal.getName());
        keycloakService.updateKeycloakUser(customerEntity, principal.getName());

        currentCustomerEntity.setEmail(customerEntity.getEmail());
        currentCustomerEntity.setFirstName(customerEntity.getFirstName());
        currentCustomerEntity.setLastName(customerEntity.getLastName());
        customerRepository.save(currentCustomerEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCustomer(String email, @Valid CustomerEntity customerEntity, CustomerRoleEnum roleOfCallingPrincipal) throws CustomerAlreadyExistsException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NoCustomerFoundException, NotAuthorizedException {
        CustomerEntity currentCustomerEntity = customerRepository.findByEmail(email).orElseThrow(NoCustomerFoundException::new);

        if (keycloakService.getRoleOfCustomer(currentCustomerEntity).compareTo(roleOfCallingPrincipal) < 0) {
            checkIfUserWithSameEmailExists(customerEntity, currentCustomerEntity.getEmail());

            keycloakService.updateKeycloakUser(customerEntity, currentCustomerEntity.getEmail());

            currentCustomerEntity.setEmail(customerEntity.getEmail());
            currentCustomerEntity.setFirstName(customerEntity.getFirstName());
            currentCustomerEntity.setLastName(customerEntity.getLastName());
            customerRepository.save(currentCustomerEntity);
        } else {
            throw new NotAuthorizedException();
        }
    }


    private CustomerEntity getCurrentCustomerIfExists(Principal principal) throws NoCustomerFoundException {
        if (principal == null) {
            throw new NoCustomerFoundException();
        }

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
