package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.AddressCreationDTO;
import de.pcmr.shop.api.model.AddressDTO;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

public interface AddressApiI {
    void createAddress(@Valid AddressCreationDTO addressCreationDTO, Principal principal) throws NoCustomerFoundException;
    List<AddressDTO> getAddresses(Principal principal) throws NoCustomerFoundException;
    void editAddress(Long addressId, @Valid AddressCreationDTO addressCreationDTO, Principal principal) throws AddressDoesNotBelongToUserException, NoCustomerFoundException, NoAddressFoundException;
    void deleteAddress(Long addressId, Principal principal) throws NoCustomerFoundException, AddressDoesNotBelongToUserException, NoAddressFoundException;
}
