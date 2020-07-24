package de.pcmr.shop.repository;

import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.domain.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
    @Override
    List<ArticleEntity> findAll();
}
