package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.AddressCreationDTO;
import de.pcmr.shop.api.model.AddressDTO;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static de.pcmr.shop.api.controller.AddressApiI.BASE_URI;

/**
 * REST-Controller for address management.
 *
 * @author Fynn Lohse
 */

@RequestMapping(BASE_URI)
public interface AddressApiI {
    String BASE_URI = "/api";
    String CUSTOMER_ADDRESS_URI = "/customer/address";

    @PostMapping(CUSTOMER_ADDRESS_URI)
    void createAddress(@RequestBody  @Valid AddressCreationDTO addressCreationDTO, Principal principal) throws NoCustomerFoundException;

    @GetMapping(CUSTOMER_ADDRESS_URI)
    List<AddressDTO> getAddresses(Principal principal) throws NoCustomerFoundException;

    @PutMapping(CUSTOMER_ADDRESS_URI + "/{id}")
    void editAddress(@PathVariable Long id, @RequestBody @Valid AddressCreationDTO addressCreationDTO, Principal principal) throws AddressDoesNotBelongToUserException, NoCustomerFoundException, NoAddressFoundException;

    @DeleteMapping(CUSTOMER_ADDRESS_URI + "/{id}")
    void deleteAddress(@PathVariable Long id, Principal principal) throws NoCustomerFoundException, AddressDoesNotBelongToUserException, NoAddressFoundException;
}
