package de.pcmr.shop.service;

import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Interface of the article service. It manages the articles that can be ordered by customers.
 * Articles can be listed, created, edited and deleted.
 *
 * @author Fynn Lohse
 */

public interface ArticleServiceI {
    /**
     * Method returns all article entities.
     *
     * @return List of all articles
     */

    List<ArticleEntity> getAllArticles();

    /**
     * Method return article entity with specific id.
     *
     * @param articleId id of article
     * @return article entity with id
     * @throws NoArticleFoundException if no article with given id could not be found
     */

    ArticleEntity getArticle(long articleId) throws NoArticleFoundException;

    /**
     * Method creates and saves new article entity.
     *
     * @param articleEntity the new article entity
     * @param imageFile article image file (may be null)
     * @throws UploadedImageResolutionTooLowException if the resolution of the image file is too low (min. 512px height)
     * @throws IOException if IO fails
     */

    void createNewArticle(@Valid ArticleEntity articleEntity, MultipartFile imageFile) throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException;

    /**
     * Method updates existing article entity.
     *
     * @param articleEntity new article entity
     * @param imageFile new article image file (may be null)
     * @throws NoArticleFoundException if no article with given id could not be found
     * @throws IOException if IO fails
     * @throws UploadedImageResolutionTooLowException if the resolution of the image file is too low (min. 512px height)
     */

    void updateArticle(@Valid ArticleEntity articleEntity, MultipartFile imageFile) throws NoArticleFoundException, IOException, UploadedImageResolutionTooLowException;

    /**
     * Method deletes existing article entity;
     *
     * @param articleId id of the article to be deleted
     * @throws NoArticleFoundException if the article with given id could not be found
     * @throws IOException if IO fails
     */

    void deleteArticle(long articleId) throws NoArticleFoundException, IOException;

    /**
     * Method returns random number of article entities.
     *
     * @param excludeId id of article not to be included in list
     * @param numberOfArticles number of articles
     * @return list of random articles in given size
     */

    List<ArticleEntity> getRandomArticles(Long excludeId, int numberOfArticles);
}
