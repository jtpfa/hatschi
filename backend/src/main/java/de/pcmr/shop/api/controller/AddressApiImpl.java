package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.AddressMapper;
import de.pcmr.shop.api.model.AddressCreationDTO;
import de.pcmr.shop.api.model.AddressDTO;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.service.AddressServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static de.pcmr.shop.api.controller.AddressApiI.BASE_URI;

/**
 * Implementation of AddressApi Interface.
 *
 * @author Fynn Lohse
 */

@RestController
@RequestMapping(BASE_URI)
public class AddressApiImpl implements AddressApiI {
    private final AddressServiceI addressService;

    @Autowired
    public AddressApiImpl(AddressServiceI addressService) {
        this.addressService = addressService;
    }

    @Override
    public void createAddress(@Valid AddressCreationDTO addressCreationDTO, Principal principal) throws NoCustomerFoundException {
        addressService.createAddress(AddressMapper.mapToAddressEntity(addressCreationDTO), principal);
    }

    @Override
    public List<AddressDTO> getAddresses(Principal principal) throws NoCustomerFoundException {
        return AddressMapper.mapToDTOList(addressService.getAddressesOfCustomer(principal));
    }

    @Override
    public void editAddress(Long id, @Valid AddressCreationDTO addressCreationDTO, Principal principal) throws AddressDoesNotBelongToUserException, NoCustomerFoundException, NoAddressFoundException {
        addressService.editAddressIfAuthenticated(AddressMapper.mapToAddressEntity(addressCreationDTO, id), principal);
    }

    @Override
    public void deleteAddress(Long id, Principal principal) throws NoCustomerFoundException, AddressDoesNotBelongToUserException, NoAddressFoundException {
        addressService.deleteAddress(id, principal);
    }
}
