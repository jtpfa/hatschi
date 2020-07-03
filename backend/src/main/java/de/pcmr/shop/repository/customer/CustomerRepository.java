package de.pcmr.shop.repository.customer;

import de.pcmr.shop.domain.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    @Override
    List<CustomerEntity> findAll();
}
