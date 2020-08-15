package de.pcmr.shop.repository;

import de.pcmr.shop.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
