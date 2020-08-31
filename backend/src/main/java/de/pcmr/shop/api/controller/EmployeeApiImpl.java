package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.CustomerMapper;
import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.domain.CustomerRoleEnum;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.service.KeycloakServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeApiImpl implements EmployeeApiI {
    private static final String EMPLOYEE_EMPLOYEE_URI = "/employee/employee";

    private final KeycloakServiceI keycloakService;

    @Autowired
    public EmployeeApiImpl(KeycloakServiceI keycloakService) {
        this.keycloakService = keycloakService;
    }

    @GetMapping(EMPLOYEE_EMPLOYEE_URI)
    public List<CustomerDetailsDTO> getAllCustomers() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        return CustomerMapper.mapUserRepresentationListToCustomerDtoList(keycloakService.findAllKeycloakUsersWithRole(CustomerRoleEnum.EMPLOYEE));
    }
}
