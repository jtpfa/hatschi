package de.pcmr.shop.service;

import de.pcmr.shop.domain.AddressEntity;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.repository.AddressRepository;
import de.pcmr.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressServiceI {
    private final AddressRepository addressRepository;
    private final CustomerServiceI customerService;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, CustomerServiceI customerService) {
        this.addressRepository = addressRepository;
        this.customerService = customerService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public List<AddressEntity> getAddressesOfCustomer(Principal principal) throws NoCustomerFoundException {
        CustomerEntity customerEntity = customerService.getCurrentCustomer(principal);
        return customerEntity.getAddresses().stream().filter(AddressEntity::isActive).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void editAddressIfAuthenticated(AddressEntity addressEntity, Principal principal) throws NoCustomerFoundException, AddressDoesNotBelongToUserException, NoAddressFoundException {
        CustomerEntity customerEntity = customerService.getCurrentCustomer(principal);
        if (addressRepository.existsById(addressEntity.getId())) {
            AddressEntity currentAddressEntity = addressRepository.findById(addressEntity.getId()).get();
            checkIfUserIsAuthorized(currentAddressEntity, customerEntity);
            checkIfAddressIsInactive(currentAddressEntity);
            addressEntity.setCustomer(customerEntity);
            addressRepository.save(addressEntity);
        } else {
            throw new NoAddressFoundException(addressEntity.getId());
        }
    }

    @Override
    @Transactional
    public void createAddress(AddressEntity addressEntity, Principal principal) throws NoCustomerFoundException {
        addressEntity.setCustomer(customerService.getCurrentCustomer(principal));
        addressRepository.save(addressEntity);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId, Principal principal) throws NoCustomerFoundException, NoAddressFoundException, AddressDoesNotBelongToUserException {
        CustomerEntity customerEntity = customerService.getCurrentCustomer(principal);
        if (addressRepository.existsById(addressId)) {
            AddressEntity currentAddressEntity = addressRepository.findById(addressId).get();
            checkIfUserIsAuthorized(currentAddressEntity, customerEntity);
            checkIfAddressIsInactive(currentAddressEntity);
            currentAddressEntity.setActive(false);
            addressRepository.save(currentAddressEntity);
        } else {
            throw new NoAddressFoundException(addressId);
        }
    }

    private void checkIfUserIsAuthorized(AddressEntity addressEntity, CustomerEntity customerEntity) throws AddressDoesNotBelongToUserException {
        if (!addressEntity.getCustomer().equals(customerEntity)) {
            throw new AddressDoesNotBelongToUserException();
        }
    }

    private void checkIfAddressIsInactive(AddressEntity addressEntity) throws NoAddressFoundException {
        if (!addressEntity.isActive()) {
            throw new NoAddressFoundException(addressEntity.getId());
        }
    }
}
