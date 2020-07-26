package de.pcmr.shop.repository;

import de.pcmr.shop.domain.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    @Override
    List<CustomerEntity> findAll();
    Optional<CustomerEntity> findByEmail(String email);
}
