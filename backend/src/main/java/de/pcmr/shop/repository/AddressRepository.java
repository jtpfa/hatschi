package de.pcmr.shop.repository;

import de.pcmr.shop.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository to access find, save and delete Address entities in the database.
 * Uses inherited JpaRepository methods.
 *
 * @author Fynn Lohse
 */

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
