package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.AddressMapper;
import de.pcmr.shop.api.model.AddressCreationDTO;
import de.pcmr.shop.api.model.AddressDTO;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.service.AddressServiceI;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(AddressApiImpl.BASE_URI)
public class AddressApiImpl implements AddressApiI {
    public static final String BASE_URI = "/api";
    public static final String CUSTOMER_ADDRESS_URI = "/customer/address";

    private final AddressServiceI addressService;

    public AddressApiImpl(AddressServiceI addressService) {
        this.addressService = addressService;
    }

    @Override
    @PostMapping(CUSTOMER_ADDRESS_URI)
    public void createAddress(@RequestBody @Valid AddressCreationDTO addressCreationDTO, Principal principal) throws NoCustomerFoundException {
        addressService.createAddress(AddressMapper.mapToAddressEntity(addressCreationDTO), principal);
    }

    @Override
    @GetMapping(CUSTOMER_ADDRESS_URI)
    public List<AddressDTO> getAddresses(Principal principal) throws NoCustomerFoundException {
        return AddressMapper.mapToDTOList(addressService.getAddressesOfCustomer(principal));
    }

    @Override
    @PutMapping(CUSTOMER_ADDRESS_URI + "/{id}")
    public void editAddress(@PathVariable Long addressId, @RequestBody @Valid AddressCreationDTO addressCreationDTO, Principal principal) throws AddressDoesNotBelongToUserException, NoCustomerFoundException, NoAddressFoundException {
        addressService.editAddressIfAuthenticated(AddressMapper.mapToAddressEntity(addressCreationDTO, addressId), principal);
    }

    @Override
    @DeleteMapping(CUSTOMER_ADDRESS_URI + "/{id}")
    public void deleteAddress(@PathVariable Long addressId, Principal principal) throws NoCustomerFoundException, AddressDoesNotBelongToUserException, NoAddressFoundException {
        addressService.deleteAddress(addressId, principal);
    }
}
