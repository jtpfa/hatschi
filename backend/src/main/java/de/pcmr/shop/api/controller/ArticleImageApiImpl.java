package de.pcmr.shop.api.controller;

import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageInvalidFileExtensionException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import de.pcmr.shop.service.ArticleImageServiceI;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ArticleImageApiImpl implements ArticleImageApiI {
    public static final String EMPLOYEE_ROLE_URI = "/employee";

    private final ArticleImageServiceI articleImageService;

    @Autowired
    public ArticleImageApiImpl(ArticleImageServiceI articleImageService) {
        this.articleImageService = articleImageService;
    }

    @Override
    @PostMapping(value = EMPLOYEE_ROLE_URI + "/article/{id}/image")
    public void uploadArticleImage(@PathVariable long id, @RequestParam(value = "file", required = true) MultipartFile imageFile) throws IOException, UploadedImageResolutionTooLowException, UploadedImageInvalidFileExtensionException, NoArticleFoundException {
        checkFileExtension(imageFile);
        articleImageService.processAndSaveArticleImage(id, imageFile);
    }

    private void checkFileExtension(MultipartFile file) throws UploadedImageInvalidFileExtensionException {
        if (!AllowedImageFileExtensionsEnum.getStringList().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            throw new UploadedImageInvalidFileExtensionException();
        }
    }
}
