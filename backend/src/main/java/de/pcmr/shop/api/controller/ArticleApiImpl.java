package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.*;
import de.pcmr.shop.api.model.ArticleCreationDTO;
import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.api.model.ArticleShortDTO;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageInvalidFileExtensionException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import de.pcmr.shop.service.ArticleServiceI;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    @GetMapping(value = "/article")
    public List<ArticleShortDTO> getArticles() {
        return ArticleMapper.mapListOfArticleEntityToListOfArticleShortDTO(articleService.getAllArticles());
    }

    @Override
    @GetMapping(value = "/article/{id}")
    public ArticleDTO getArticle(@PathVariable long id) throws NoArticleFoundException {
        return ArticleMapper.mapArticleEntityToArticleDTO(articleService.getArticle(id));
    }

    @Override
    @GetMapping(value = EMPLOYEE_ROLE_URI + "/article")
    public List<ArticleDTO> getArticlesFull() {
        return ArticleMapper.mapListOfArticleEntitiesToListOfArticleDTO(articleService.getAllArticles());
    }

    @Override
    @PostMapping(value = EMPLOYEE_ROLE_URI + "/article")
    public void createArticle(@Valid @RequestPart(value = "json", required = true) ArticleCreationDTO articleCreationDTO, @RequestPart(value = "file", required = false) MultipartFile imageFile) throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException, UploadedImageInvalidFileExtensionException {
        if (imageFile != null) {
            checkFileExtension(imageFile);
        }
        articleService.createNewArticle(ArticleMapper.mapArticleCreationDTOToArticleEntity(articleCreationDTO), imageFile);
    }

    @Override
    @PutMapping(value = EMPLOYEE_ROLE_URI + "/article")
    public void updateArticle(@Valid @RequestPart(value = "json", required = true) ArticleDTO articleDTO, @RequestPart(value = "file", required = false) MultipartFile imageFile) throws NoArticleFoundException, IOException, UploadedImageResolutionTooLowException, UploadedImageInvalidFileExtensionException {
        if (imageFile != null) {
            checkFileExtension(imageFile);
        }
        articleService.updateArticle(ArticleMapper.mapArticleDTOToArticleEntity(articleDTO), imageFile);
    }

    @Override
    @DeleteMapping(value = EMPLOYEE_ROLE_URI + "/article/{id}")
    public void deleteArticle(@PathVariable long id) throws NoArticleFoundException, IOException {
        articleService.deleteArticle(id);
    }

    private void checkFileExtension(MultipartFile file) throws UploadedImageInvalidFileExtensionException {
        if (!AllowedImageFileExtensionsEnum.getStringList().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            throw new UploadedImageInvalidFileExtensionException();
        }
    }
}