package de.pcmr.shop.api.controller;

import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageInvalidFileExtensionException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArticleImageApiI {
    void uploadArticleImage(long id, MultipartFile imageFile) throws IOException, UploadedImageResolutionTooLowException, UploadedImageInvalidFileExtensionException, NoArticleFoundException;
}
