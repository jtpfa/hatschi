package de.pcmr.shop.service;

import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.exception.NoArticleFoundException;

import javax.validation.Valid;
import java.util.List;

public interface ArticleServiceI {
    List<ArticleEntity> getAllArticles();
    ArticleEntity getArticle(long articleId) throws NoArticleFoundException;
    void createNewArticle(@Valid ArticleEntity articleEntity);
    void updateArticle(@Valid ArticleEntity articleEntity) throws NoArticleFoundException;
    void deleteArticle(long articleId) throws NoArticleFoundException;
}
