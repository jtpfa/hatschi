package de.pcmr.shop.repository;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomer(CustomerEntity customerEntity);
}
