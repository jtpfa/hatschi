package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.ArticleCreationDTO;
import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.api.model.ArticleShortDTO;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageInvalidFileExtensionException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

public interface ArticleApiI {
    List<ArticleShortDTO> getArticles();
    List<ArticleShortDTO> getRandomArticles(Integer limit);
    ArticleDTO getArticle(long id) throws NoArticleFoundException;
    void createArticle(@Valid ArticleCreationDTO articleCreationDTO, MultipartFile imageFile) throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException, UploadedImageInvalidFileExtensionException;
    void updateArticle(@Valid ArticleDTO articleDTO, MultipartFile imageFile) throws NoArticleFoundException, IOException, UploadedImageResolutionTooLowException, UploadedImageInvalidFileExtensionException;
    void deleteArticle(long id) throws NoArticleFoundException, IOException;
    List<ArticleDTO> getArticlesFull();
}
