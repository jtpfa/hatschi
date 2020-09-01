package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.CustomerMapper;
import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.api.model.CustomerDetailsRoleDTO;
import de.pcmr.shop.domain.CustomerRoleEnum;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotAuthorizedException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.service.CustomerServiceI;
import de.pcmr.shop.service.KeycloakServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeApiImpl implements EmployeeApiI {
    private static final String EMPLOYEE_EMPLOYEE_URI = "/employee/employee";
    private static final String EMPLOYEE_ADMIN_URI = "/admin/employee";

    private final KeycloakServiceI keycloakService;
    private final CustomerServiceI customerService;

    @Autowired
    public EmployeeApiImpl(KeycloakServiceI keycloakService, CustomerServiceI customerService) {
        this.keycloakService = keycloakService;
        this.customerService = customerService;
    }

    @Override
    @GetMapping(EMPLOYEE_EMPLOYEE_URI)
    public List<CustomerDetailsDTO> getAllEmployees() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        return CustomerMapper.mapUserRepresentationListToCustomerDtoList(keycloakService.findAllKeycloakUsersWithRole(CustomerRoleEnum.EMPLOYEE));
    }

    @Override
    @PutMapping(EMPLOYEE_ADMIN_URI + "/{email:.+}")
    public void updateEmployee(@PathVariable String email, @RequestBody @Valid CustomerDetailsRoleDTO customerDetailsRoleDTO) throws NotAuthorizedException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException {
        customerService.updateCustomer(email, CustomerMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsRoleDTO), CustomerRoleEnum.ADMIN, customerDetailsRoleDTO.getCustomerRole());
    }

    @Override
    @DeleteMapping(EMPLOYEE_ADMIN_URI + "/{email:.+}")
    public void deleteEmployee(@PathVariable String email) throws KeycloakEndpointNotFoundException, NotAuthorizedException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException {
        customerService.deleteCustomer(email, CustomerRoleEnum.ADMIN);
    }
}
