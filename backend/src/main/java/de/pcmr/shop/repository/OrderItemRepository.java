package de.pcmr.shop.repository;

import de.pcmr.shop.domain.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository to access find, save and delete OrderItem entities in the database.
 * Uses inherited JpaRepository methods.
 *
 * @author Fynn Lohse
 */

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
