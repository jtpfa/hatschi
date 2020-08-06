package de.pcmr.shop.service;

import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArticleImageServiceI {
    void processAndSaveArticleImage(long articleId, MultipartFile imageFile) throws IOException, UploadedImageResolutionTooLowException, NoArticleFoundException;
    void deleteArticleImages(long articleId) throws NoArticleFoundException, IOException;
}
