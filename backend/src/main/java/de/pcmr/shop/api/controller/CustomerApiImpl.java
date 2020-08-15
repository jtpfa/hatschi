package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.CustomerDetailsDTOCustomerEntityMapper;
import de.pcmr.shop.api.mapper.CustomerEntityCustomerDetailsDTOMapper;
import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.exception.CustomerAlreadyExistsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.service.CustomerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(CustomerApiImpl.CUSTOMER_URI)
public class CustomerApiImpl implements CustomerApiI {
    public static final String CUSTOMER_URI = "/api/customer";

    private final CustomerServiceI customerService;

    @Autowired
    public CustomerApiImpl(CustomerServiceI customerService) {
        this.customerService = customerService;
    }

    @Override
    @GetMapping
    public CustomerDetailsDTO getCustomer(Principal principal) throws NoCustomerFoundException {
        return CustomerEntityCustomerDetailsDTOMapper.mapCustomerEntityToCustomerDetailsDto(customerService.getCurrentCustomer(principal));
    }

    @Override
    @PutMapping
    public void updateCustomer(@RequestBody @Valid CustomerDetailsDTO customerDetailsDTO, Principal principal) throws NoCustomerFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        customerService.updateCurrentCustomer(CustomerDetailsDTOCustomerEntityMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsDTO), principal);
    }
}
