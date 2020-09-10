package de.pcmr.shop.repository;

import de.pcmr.shop.domain.ArticleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * JpaRepository to access find, save and delete Article entities in the database.
 * Uses inherited JpaRepository methods.
 *
 * @author Fynn Lohse
 */

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    /**
     * Finds a random number of articles.
     *
     * @param excludeId the id of an article which should not be included in result list
     * @param pageable pageable object to specify number of random articles
     * @return list of random articles
     */

    @Query(value="SELECT a FROM ArticleEntity a where a.id <> ?1 order by function('RAND')")
    List<ArticleEntity> findRandomArticles(Long excludeId, Pageable pageable);
}
