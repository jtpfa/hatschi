package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.ArticleCreationDTOArticleEntityMapper;
import de.pcmr.shop.api.mapper.ArticleDTOArticleEntityMapper;
import de.pcmr.shop.api.mapper.ArticleEntityArticleDTOMapper;
import de.pcmr.shop.api.mapper.ArticleEntityArticleShortDTOMapper;
import de.pcmr.shop.api.model.ArticleCreationDTO;
import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.api.model.ArticleShortDTO;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.service.ArticleServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ArticleApiImpl.BASE_URI)
public class ArticleApiImpl implements ArticleApiI {
    public static final String BASE_URI = "/api";
    public static final String EMPLOYEE_ROLE_URI = "/employee";

    private final ArticleServiceI articleService;

    @Autowired
    public ArticleApiImpl(ArticleServiceI articleService) {
        this.articleService = articleService;
    }

    @Override
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public List<ArticleShortDTO> getArticles() {
        return ArticleEntityArticleShortDTOMapper.mapListOfArticleEntityToListOfArticleShortDTO(articleService.getAllArticles());
    }

    @Override
    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    public ArticleDTO getArticle(@PathVariable long id) throws NoArticleFoundException {
        return ArticleEntityArticleDTOMapper.mapArticleEntityToArticleDTO(articleService.getArticle(id));
    }

    @Override
    @RequestMapping(value = EMPLOYEE_ROLE_URI + "/article", method = RequestMethod.POST)
    public void createArticle(@Valid @RequestBody ArticleCreationDTO articleCreationDTO) {
        articleService.createNewArticle(ArticleCreationDTOArticleEntityMapper.mapArticleCreationDTOToArticleEntity(articleCreationDTO));
    }

    @Override
    @RequestMapping(value = EMPLOYEE_ROLE_URI + "/article", method = RequestMethod.PUT)
    public void updateArticle(@Valid @RequestBody ArticleDTO articleDTO) throws NoArticleFoundException {
        articleService.updateArticle(ArticleDTOArticleEntityMapper.mapArticleDTOToArticleEntity(articleDTO));
    }

    @Override
    @RequestMapping(value = EMPLOYEE_ROLE_URI + "/article/{id}", method = RequestMethod.DELETE)
    public void deleteArticle(@PathVariable long id) throws NoArticleFoundException {
        articleService.deleteArticle(id);
    }
}