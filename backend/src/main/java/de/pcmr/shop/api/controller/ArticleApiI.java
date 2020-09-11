package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.ArticleCreationDTO;
import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.api.model.ArticleShortDTO;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageInvalidFileExtensionException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static de.pcmr.shop.api.controller.ArticleApiI.BASE_URI;

@RequestMapping(BASE_URI)
public interface ArticleApiI {
    String BASE_URI = "/api";
    String EMPLOYEE_ROLE_URI = "/employee";

    @GetMapping(value = "/article")
    List<ArticleShortDTO> getArticles();

    @GetMapping("/article/random/{excludeId}/{limit}")
    List<ArticleShortDTO> getRandomArticles(@PathVariable Long excludeId, @PathVariable Integer limit);

    @GetMapping(value = "/article/{id}")
    ArticleDTO getArticle(@PathVariable long id) throws NoArticleFoundException;

    @GetMapping(value = EMPLOYEE_ROLE_URI + "/article")
    List<ArticleDTO> getArticlesFull();

    @PostMapping(value = EMPLOYEE_ROLE_URI + "/article")
    void createArticle(@Valid @RequestPart(value = "json") ArticleCreationDTO articleCreationDTO, @RequestPart(value = "file", required = false) MultipartFile imageFile) throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException, UploadedImageInvalidFileExtensionException;

    @PutMapping(value = EMPLOYEE_ROLE_URI + "/article")
    void updateArticle(@Valid @RequestPart(value = "json") ArticleDTO articleDTO, @RequestPart(value = "file", required = false) MultipartFile imageFile) throws NoArticleFoundException, IOException, UploadedImageResolutionTooLowException, UploadedImageInvalidFileExtensionException;

    @DeleteMapping(value = EMPLOYEE_ROLE_URI + "/article/{id}")
    void deleteArticle(@PathVariable long id) throws NoArticleFoundException, IOException;
}
