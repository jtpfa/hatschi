package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.CustomerMapper;
import de.pcmr.shop.api.model.CustomerDetailsDTO;
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
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerApiImpl implements CustomerApiI {
    public static final String CUSTOMER_URI = "/customer";
    public static final String CUSTOMER_EMPLOYEE_URI = "/employee/customer";

    private final CustomerServiceI customerService;
    private final KeycloakServiceI keycloakService;

    @Autowired
    public CustomerApiImpl(CustomerServiceI customerService, KeycloakServiceI keycloakService) {
        this.customerService = customerService;
        this.keycloakService = keycloakService;
    }

    @Override
    @GetMapping(CUSTOMER_URI)
    public CustomerDetailsDTO getCustomer(Principal principal) throws NoCustomerFoundException {
        return CustomerMapper.mapCustomerEntityToCustomerDetailsDto(customerService.getCurrentCustomer(principal));
    }

    @Override
    @PutMapping(CUSTOMER_URI)
    public void updateCustomer(@RequestBody @Valid CustomerDetailsDTO customerDetailsDTO, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        customerService.updateCurrentCustomer(CustomerMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsDTO), principal);
    }

    @GetMapping(CUSTOMER_EMPLOYEE_URI)
    public List<CustomerDetailsDTO> getAllCustomers() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        return CustomerMapper.mapUserRepresentationListToCustomerDtoList(keycloakService.findAllKeycloakUsersWithRole(CustomerRoleEnum.CUSTOMER));
    }

    @PutMapping(CUSTOMER_EMPLOYEE_URI + "/{email:.+}")
    public void updateCustomer(@PathVariable String email, @RequestBody @Valid CustomerDetailsDTO customerDetailsDTO) throws CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, NotAuthorizedException {
        customerService.updateCustomer(email, CustomerMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsDTO), CustomerRoleEnum.EMPLOYEE);
    }
}
