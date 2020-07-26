package de.pcmr.shop.service;

import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArticleImageServiceI {
    public static final List<String> ALLOWED_FILE_EXTENSIONS = List.of("png", "jpg");
    void processAndSaveArticleImage(long articleId, MultipartFile imageFile) throws IOException, UploadedImageResolutionTooLowException, NoArticleFoundException;
}
