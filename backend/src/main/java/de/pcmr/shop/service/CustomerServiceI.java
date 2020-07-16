package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.NoCustomerFoundException;

import java.security.Principal;

public interface CustomerServiceI {
    CustomerEntity getCurrentCustomer(Principal principal) throws NoCustomerFoundException;
}
