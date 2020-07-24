package de.pcmr.shop.service;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerServiceI {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity getCurrentCustomer(Principal principal) throws NoCustomerFoundException {
        Optional<CustomerEntity> customerEntity = customerRepository.findByEmail(principal.getName());
        if (customerEntity.isPresent()) {
            return customerEntity.get();
        } else {
            throw new NoCustomerFoundException();
        }
    }
}
