package de.pcmr.shop.service;

import de.pcmr.shop.domain.AddressEntity;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;

import java.security.Principal;
import java.util.List;

public interface AddressServiceI {
    List<AddressEntity> getAddressesOfCustomer(Principal principal) throws NoCustomerFoundException;
    void editAddressIfAuthenticated(AddressEntity addressEntity, Principal principal) throws NoCustomerFoundException, AddressDoesNotBelongToUserException, NoAddressFoundException;
    void createAddress(AddressEntity addressEntity, Principal principal) throws NoCustomerFoundException;
    void deleteAddress(Long addressId, Principal principal) throws NoCustomerFoundException, NoAddressFoundException, AddressDoesNotBelongToUserException;
}
