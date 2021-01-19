package de.pcmr.shop.repository;

import de.pcmr.shop.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JpaRepository to access find, save and delete Customer entities in the database.
 * Uses inherited JpaRepository methods.
 *
 * @author Fynn Lohse
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    /**
     * Finds a customer by it's email.
     *
     * @param email email address of customer
     * @return customer with given email
     */
    Optional<CustomerEntity> findByEmail(String email);
}
