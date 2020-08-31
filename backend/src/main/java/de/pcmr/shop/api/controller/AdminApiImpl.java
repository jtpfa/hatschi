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
public class AdminApiImpl implements AdminApiI {
    private static final String ADMIN_ADMIN_URI = "/admin/admin";

    private final CustomerServiceI customerService;
    private final KeycloakServiceI keycloakService;

    @Autowired
    public AdminApiImpl(CustomerServiceI customerService, KeycloakServiceI keycloakService) {
        this.customerService = customerService;
        this.keycloakService = keycloakService;
    }

    @Override
    @GetMapping(ADMIN_ADMIN_URI)
    public List<CustomerDetailsDTO> getAllAdmins() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        return CustomerMapper.mapUserRepresentationListToCustomerDtoList(keycloakService.findAllKeycloakUsersWithRole(CustomerRoleEnum.ADMIN));
    }

    @Override
    @PutMapping(ADMIN_ADMIN_URI + "/{email:.+}")
    public void updateEmployee(@PathVariable String email, @RequestBody @Valid CustomerDetailsRoleDTO customerDetailsRoleDTO) throws NotAuthorizedException, CustomerAlreadyExistsException, KeycloakEndpointNotFoundException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException {
        customerService.updateCustomer(email, CustomerMapper.mapCustomerDetailsDTOToCustomerEntity(customerDetailsRoleDTO), CustomerRoleEnum.ADMIN, customerDetailsRoleDTO.getCustomerRoleEnum());
    }

    @Override
    @DeleteMapping(ADMIN_ADMIN_URI + "/{email:.+}")
    public void deleteEmployee(@PathVariable String email) throws KeycloakEndpointNotFoundException, NotAuthorizedException, NoCustomerFoundException, KeycloakUserIsNotAuthorizedException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException {
        customerService.deleteCustomer(email, CustomerRoleEnum.ADMIN);
    }
}
