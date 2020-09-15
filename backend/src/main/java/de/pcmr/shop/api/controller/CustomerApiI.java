package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.api.model.CustomerDetailsRoleDTO;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotAuthorizedException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * REST-Controller for customer user management.
 *
 * @author Fynn Lohse
 */

@RequestMapping("/api")
public interface CustomerApiI {
    String CUSTOMER_URI = "/customer";
    String CUSTOMER_EMPLOYEE_URI = "/employee/customer";
    String CUSTOMER_ADMIN_URI = "/admin/customer";

    @GetMapping(CUSTOMER_URI)
    CustomerDetailsDTO getCustomer(Principal principal) throws NoCustomerFoundException;

    @PutMapping(CUSTOMER_URI)
    void updateCustomer(@RequestBody @Valid CustomerDetailsDTO customerDetailsDTO, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException;

    @DeleteMapping(CUSTOMER_ADMIN_URI + "/{email:.+}")
    void deleteCustomer(@PathVariable String email) throws KeycloakEndpointNotFoundException, NotAuthorizedException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException;

    @GetMapping(CUSTOMER_EMPLOYEE_URI)
    List<CustomerDetailsDTO> getAllCustomers() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException;

    @PutMapping(CUSTOMER_EMPLOYEE_URI + "/{email:.+}")
    void updateCustomer(@PathVariable String email, @RequestBody @Valid CustomerDetailsDTO customerDetailsDTO) throws CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, NotAuthorizedException;

    @PutMapping(CUSTOMER_ADMIN_URI + "/{email:.+}")
    void updateCustomer(@PathVariable String email, @RequestBody @Valid CustomerDetailsRoleDTO customerDetailsRoleDTO) throws CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, NotAuthorizedException;
}
