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
import java.util.List;

/**
 * REST-Controller for employee user management.
 *
 * @author Fynn Lohse
 */

@RequestMapping("/api")
public interface EmployeeApiI {
    String EMPLOYEE_EMPLOYEE_URI = "/employee/employee";
    String EMPLOYEE_ADMIN_URI = "/admin/employee";

    @GetMapping(EMPLOYEE_EMPLOYEE_URI)
    List<CustomerDetailsDTO> getAllEmployees() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException;

    @PutMapping(EMPLOYEE_ADMIN_URI + "/{email:.+}")
    void updateEmployee(@PathVariable String email, @RequestBody @Valid CustomerDetailsRoleDTO customerDetailsRoleDTO) throws NotAuthorizedException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException;

    @DeleteMapping(EMPLOYEE_ADMIN_URI + "/{email:.+}")
    void deleteEmployee(@PathVariable String email) throws KeycloakEndpointNotFoundException, NotAuthorizedException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException;

}
