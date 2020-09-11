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

import javax.validation.Valid;
import java.security.Principal;

/**
 * Interface of the customer service. It manages the customers.
 * Customers can be get, updates and deleted.
 *
 * @author Fynn Lohse
 */

public interface CustomerServiceI {

    /**
     * Method returns currently authenticated customer entity determined by the principal.
     *
     * @param principal principal of spring security context
     * @return current customer entity
     * @throws NoCustomerFoundException if no customer could be determined by the principal
     */

    CustomerEntity getCurrentCustomer(Principal principal) throws NoCustomerFoundException;

    /**
     * Method updates currently authenticated customer entity determined by the principal.
     *
     * @param customerEntity customer entity with new details
     * @param principal principal of spring security context
     * @throws NoCustomerFoundException if no customer could be determined by the principal
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserAlreadyExistsException if there is an existing keycloak user with same email
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     * @throws CustomerAlreadyExistsException if there is an existing customer with same email
     */

    void updateCurrentCustomer(@Valid CustomerEntity customerEntity, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException;

    /**
     * Method updates any customer determined by its email.
     *
     * @param email current customers email
     * @param customerEntity customer entity with new details
     * @param roleOfCallingPrincipal role of calling principal
     * @throws CustomerAlreadyExistsException if there is an existing customer with same email
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserAlreadyExistsException if there is an existing keycloak user with same email
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     * @throws NoCustomerFoundException if no customer could be determined by the email
     * @throws NotAuthorizedException if caller is not authorized to update the customers details
     */

    void updateCustomer(String email, @Valid CustomerEntity customerEntity, CustomerRoleEnum roleOfCallingPrincipal) throws CustomerAlreadyExistsException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NoCustomerFoundException, NotAuthorizedException;

    /**
     * Method updates any customer determined by its email. It also updates the customers role.
     *
     * @param email current customers email
     * @param customerEntity customer entity with new details
     * @param roleOfCallingPrincipal role of calling principal
     * @param targetCustomerRole new role of customer
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserAlreadyExistsException if there is an existing keycloak user with same email
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     * @throws CustomerAlreadyExistsException if there is an existing customer with same email
     * @throws NotAuthorizedException if caller is not authorized to update the customers details
     * @throws NoCustomerFoundException if no customer could be determined by the email
     */

    void updateCustomer(String email, @Valid CustomerEntity customerEntity, CustomerRoleEnum roleOfCallingPrincipal, CustomerRoleEnum targetCustomerRole) throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, CustomerAlreadyExistsException, NotAuthorizedException, NoCustomerFoundException;

    /**
     * Method deletes any customer determined by its email.
     *
     * @param email current customers email
     * @param roleOfCallingPrincipal role of calling principal
     * @throws NoCustomerFoundException if no customer could be determined by the email
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserAlreadyExistsException if there is an existing keycloak user with same email
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     * @throws NotAuthorizedException if caller is not authorized to delete the customer
     */

    void deleteCustomer(String email, CustomerRoleEnum roleOfCallingPrincipal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException, NotAuthorizedException;
}
