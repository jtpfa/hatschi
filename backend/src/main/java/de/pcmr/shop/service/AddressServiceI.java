package de.pcmr.shop.service;

import de.pcmr.shop.domain.AddressEntity;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;

import java.security.Principal;
import java.util.List;

/**
 * Interface of the address service. The address service manages the addresses of the customers.
 * Addresses of an customer can be listed, created, edited and deleted.
 *
 * @author Fynn Lohse
 */

public interface AddressServiceI {
    /**
     * Method returns List of address entities that belong to the currently authenticated customer determined by the principal.
     *
     * @param principal principal of spring security context
     * @return List of address entities that belong to the logged in user
     * @throws NoCustomerFoundException if no customer could be determined by the principal
     */

    List<AddressEntity> getAddressesOfCustomer(Principal principal) throws NoCustomerFoundException;

    /**
     * Method to edit an existing address entity that belongs to the currently authenticated customer determined by the principal.
     *
     * @param addressEntity the edited address entity
     * @param principal principal of spring security context
     * @throws NoCustomerFoundException if no customer could be determined by the principal
     * @throws AddressDoesNotBelongToUserException if the currently authenticated customer is not the owner of the address
     * @throws NoAddressFoundException if no address could be found to the given id
     */

    void editAddressIfAuthenticated(AddressEntity addressEntity, Principal principal) throws NoCustomerFoundException, AddressDoesNotBelongToUserException, NoAddressFoundException;

    /**
     * Method saves new address entity for the currently authenticated customer determined by the principal.
     *
     * @param addressEntity new address entity
     * @param principal principal of spring security context
     * @throws NoCustomerFoundException if no customer could be determined by the principal
     */

    void createAddress(AddressEntity addressEntity, Principal principal) throws NoCustomerFoundException;

    /**
     * Method deletes existing address entity that belongs to the currently authenticated customer determined by the principal.
     *
     * @param addressId the id of the address to be deleted
     * @param principal principal of spring security context
     * @throws NoCustomerFoundException if no customer could be determined by the principal
     * @throws NoAddressFoundException if no address could be found to the given id
     * @throws AddressDoesNotBelongToUserException if the currently authenticated customer is not the owner of the address
     */

    void deleteAddress(Long addressId, Principal principal) throws NoCustomerFoundException, NoAddressFoundException, AddressDoesNotBelongToUserException;
}
