package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.ArticleCreationDTO;
import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.api.model.ArticleShortDTO;
import de.pcmr.shop.exception.NoArticleFoundException;

import javax.validation.Valid;
import java.util.List;

public interface ArticleApiI {
    List<ArticleShortDTO> getArticles();
    ArticleDTO getArticle(long id) throws NoArticleFoundException;
    void createArticle(@Valid ArticleCreationDTO articleCreationDTO);
    void updateArticle(@Valid ArticleDTO articleDTO) throws NoArticleFoundException;
    void deleteArticle(long id) throws NoArticleFoundException;
    List<ArticleDTO> getArticlesFull();
}
