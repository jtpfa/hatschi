package de.pcmr.shop.service;

import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

public interface ArticleServiceI {
    List<ArticleEntity> getAllArticles();
    ArticleEntity getArticle(long articleId) throws NoArticleFoundException;
    void createNewArticle(@Valid ArticleEntity articleEntity, MultipartFile imageFile) throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException;
    void updateArticle(@Valid ArticleEntity articleEntity, MultipartFile imageFile) throws NoArticleFoundException, IOException, UploadedImageResolutionTooLowException;
    void deleteArticle(long articleId) throws NoArticleFoundException, IOException;
    List<ArticleEntity> getRandomArticles(int numberOfArticles);
}
