package de.pcmr.shop.api.controller.customer;

import de.pcmr.shop.api.mapper.CustomerEntityCustomerDetailsDtoMapper;
import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.service.CustomerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(CustomerApiImpl.REGISTRATION_URI)
public class CustomerApiImpl implements CustomerApiI {
    public static final String REGISTRATION_URI = "/api/customer";

    private final CustomerServiceI customerService;

    @Autowired
    public CustomerApiImpl(CustomerServiceI customerService) {
        this.customerService = customerService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public CustomerDetailsDTO getCustomer(Principal principal) throws NoCustomerFoundException {
        return CustomerEntityCustomerDetailsDtoMapper.mapCustomerEntityToCustomerDetailsDto(customerService.getCurrentCustomer(principal));
    }
}
