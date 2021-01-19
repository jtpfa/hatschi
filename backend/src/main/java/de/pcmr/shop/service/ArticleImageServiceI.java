package de.pcmr.shop.service;

import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Interface of the article image service. It manages the uploaded article images and saves them in the file system.
 *
 * @author Fynn Lohse
 */
public interface ArticleImageServiceI {

    /**
     * Method resizes an uploaded image (png, jpg) to three different heights (256px, 512px, 1024px) while keeping its aspect ratio.
     * It also converts jpgs to pngs and saves them to the file system.
     *
     * @param articleId id of the article to which the images are processed
     * @param imageFile MultipartFile representation of the image file
     * @throws IOException if IO fails
     * @throws UploadedImageResolutionTooLowException if the uploaded image has a too low resolution (min. 512px height)
     * @throws NoArticleFoundException if no article could be found to the given id
     */

    void processAndSaveArticleImage(long articleId, MultipartFile imageFile) throws IOException, UploadedImageResolutionTooLowException, NoArticleFoundException;

    /**
     * Method deletes saved images of an article from the file system.
     *
     * @param articleId id of the article to which the images are deleted
     * @throws NoArticleFoundException if no article could be found to the given id
     * @throws IOException if IO fails
     */

    void deleteArticleImages(long articleId) throws NoArticleFoundException, IOException;
}
