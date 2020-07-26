package de.pcmr.shop.repository;

import de.pcmr.shop.domain.ArticleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
    @Override
    List<ArticleEntity> findAll();
}
