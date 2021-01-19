package de.pcmr.shop.repository;

import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository to access find, save and delete Order entities in the database.
 * Uses inherited JpaRepository methods.
 *
 * @author Fynn Lohse
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    /**
     * Finds all orders by it's customer.
     *
     * @param customerEntity customer object
     * @return List of OrderEntity objects
     */
    List<OrderEntity> findByCustomer(CustomerEntity customerEntity);
}
