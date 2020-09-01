package de.pcmr.shop.repository;

import de.pcmr.shop.domain.ArticleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    @Query(value="SELECT a FROM ArticleEntity a where a.id <> ?1 order by function('RAND')")
    List<ArticleEntity> findRandomArticles(Long excludeId, Pageable pageable);
}
