package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.domain.CustomerRoleEnum;
import de.pcmr.shop.exception.NotAuthorizedException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

/**
 * Interface of keycloak service. It communicates with the keycloak admin rest api and manages the keycloak users.
 *
 * @author Fynn Lohse
 */

public interface KeycloakServiceI {

    /**
     * Method creates a new keycloak user based on an customer entity.
     *
     * @param customerEntity customer entity
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     * @throws KeycloakUserAlreadyExistsException if keycloak user already exists
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     */

    void createKeycloakUser(CustomerEntity customerEntity) throws KeycloakUserIsNotAuthorizedException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUnknownErrorException;

    /**
     * Method updates existing keycloak user based on customer entity.
     *
     * @param customerEntity customer entity
     * @param currentUsername current username (email)
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     * @throws KeycloakUserAlreadyExistsException if there is an existing customer with same email
     */

    void updateKeycloakUser(CustomerEntity customerEntity, String currentUsername) throws KeycloakEndpointNotFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException;

    /**
     * Method lists all keycloak users with certain role
     *
     * @param roleEnum customer role
     * @return List of keycloak users with certain role as UserRepresentation
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     */

    List<UserRepresentation> findAllKeycloakUsersWithRole(CustomerRoleEnum roleEnum) throws KeycloakEndpointNotFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException;

    /**
     * Method gets role of keycloak user based on customer entity.
     *
     * @param customerEntity customer entity
     * @return customer role
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     */

    CustomerRoleEnum getRoleOfCustomer(CustomerEntity customerEntity) throws KeycloakEndpointNotFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException;

    /**
     * Method gets role of keycloak user based on users email (username)
     *
     * @param userEmail email of customer
     * @return customer role
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     */

    CustomerRoleEnum getRoleOfCustomer(String userEmail) throws KeycloakEndpointNotFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException;

    /**
     * Method updates existing keycloak user based on customer entity and sets a new user role.
     *
     * @param customerEntity customer entity
     * @param currentUsername current username (email)
     * @param customerRoleEnum customer target role
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     */

    void updateKeycloakUser(CustomerEntity customerEntity, String currentUsername, CustomerRoleEnum customerRoleEnum) throws KeycloakEndpointNotFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException;

    /**
     * Method deletes keycloak user by its username (email).
     *
     * @param email customers email (username)
     * @throws KeycloakEndpointNotFoundException if keycloak endpoint was not found
     * @throws KeycloakUnknownErrorException if there was any other keycloak error
     * @throws KeycloakUserIsNotAuthorizedException if keycloak admin user is not authorized
     * @throws NotAuthorizedException if the master admin user is tried to be deleted
     * @throws KeycloakUserAlreadyExistsException if there is an existing customer with same email
     */

    void deleteCustomerByEmail(String email) throws KeycloakEndpointNotFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException, NotAuthorizedException;
}
