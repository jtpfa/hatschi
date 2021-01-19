package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.ArticleMapper;
import de.pcmr.shop.api.model.ArticleCreationDTO;
import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.api.model.ArticleShortDTO;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageInvalidFileExtensionException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import de.pcmr.shop.service.ArticleServiceI;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static de.pcmr.shop.api.controller.ArticleApiI.BASE_URI;

/**
 * Implementation of ArticleApi Interface.
 *
 * @author Fynn Lohse
 */

@RestController
@RequestMapping(BASE_URI)
public class ArticleApiImpl implements ArticleApiI {
    private final ArticleServiceI articleService;

    @Autowired
    public ArticleApiImpl(ArticleServiceI articleService) {
        this.articleService = articleService;
    }

    @Override
    public List<ArticleShortDTO> getArticles() {
        return ArticleMapper.mapListOfArticleEntityToListOfArticleShortDTO(articleService.getAllArticles());
    }

    @Override
    public List<ArticleShortDTO> getRandomArticles(Long excludeId, Integer limit) {
        return ArticleMapper.mapListOfArticleEntityToListOfArticleShortDTO(articleService.getRandomArticles(excludeId, limit));
    }

    @Override
    public ArticleDTO getArticle(long id) throws NoArticleFoundException {
        return ArticleMapper.mapArticleEntityToArticleDTO(articleService.getArticle(id));
    }

    @Override
    public List<ArticleDTO> getArticlesFull() {
        return ArticleMapper.mapListOfArticleEntitiesToListOfArticleDTO(articleService.getAllArticles());
    }

    @Override
    public void createArticle(@Valid ArticleCreationDTO articleCreationDTO, MultipartFile imageFile) throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException, UploadedImageInvalidFileExtensionException {
        if (imageFile != null) {
            checkFileExtension(imageFile);
        }
        articleService.createNewArticle(ArticleMapper.mapArticleCreationDTOToArticleEntity(articleCreationDTO), imageFile);
    }

    @Override
    public void updateArticle(@Valid ArticleDTO articleDTO, MultipartFile imageFile) throws NoArticleFoundException, IOException, UploadedImageResolutionTooLowException, UploadedImageInvalidFileExtensionException {
        if (imageFile != null) {
            checkFileExtension(imageFile);
        }
        articleService.updateArticle(ArticleMapper.mapArticleDTOToArticleEntity(articleDTO), imageFile);
    }

    @Override
    public void deleteArticle(long id) throws NoArticleFoundException, IOException {
        articleService.deleteArticle(id);
    }

    private void checkFileExtension(MultipartFile file) throws UploadedImageInvalidFileExtensionException {
        if (!AllowedImageFileExtensionsEnum.getStringList().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            throw new UploadedImageInvalidFileExtensionException();
        }
    }
}