package de.pcmr.shop.api.controller.customer;

import de.pcmr.shop.api.model.CustomerDetailsDTO;
import de.pcmr.shop.exception.NoCustomerFoundException;

import java.security.Principal;

public interface CustomerApiI {
    CustomerDetailsDTO getCustomer(Principal principal) throws NoCustomerFoundException;
}
