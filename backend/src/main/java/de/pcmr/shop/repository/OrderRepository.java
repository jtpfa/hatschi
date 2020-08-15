package de.pcmr.shop.repository;

import de.pcmr.shop.domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
