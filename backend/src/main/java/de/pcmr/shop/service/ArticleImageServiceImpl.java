package de.pcmr.shop.service;

import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import de.pcmr.shop.repository.ArticleRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ArticleImageServiceImpl implements ArticleImageServiceI {

    private static final String IMAGE_PATH = "./media/article/";
    private static final int SCALE_IMAGE_HEIGHT_LOW = 256;
    private static final int SCALE_IMAGE_HEIGHT_MID = 512;
    private static final int SCALE_IMAGE_HEIGHT_HIGH = 1024;
    private static final int MIN_UPLOAD_IMAGE_HEIGHT = SCALE_IMAGE_HEIGHT_MID;
    private static final String PNG = "png";

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleImageServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void processAndSaveArticleImage(long articleId, MultipartFile imageFile) throws IOException, UploadedImageResolutionTooLowException, NoArticleFoundException {
        checkIfArticleWithIdExits(articleId);

        final String fileExtension = FilenameUtils.getExtension(imageFile.getOriginalFilename());
        Path articleImagePath = Paths.get(IMAGE_PATH);

        createPathIfNotExists(articleImagePath);
        deleteExistingArticleImagesIfExisting(articleId);

        BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
        resizeAndWriteImageToFilesystem(articleId, bufferedImage, articleImagePath, fileExtension, SCALE_IMAGE_HEIGHT_HIGH);
        resizeAndWriteImageToFilesystem(articleId, bufferedImage, articleImagePath, fileExtension, SCALE_IMAGE_HEIGHT_MID);
        resizeAndWriteImageToFilesystem(articleId, bufferedImage, articleImagePath, fileExtension, SCALE_IMAGE_HEIGHT_LOW);
    }

    @Override
    public void deleteArticleImages(long articleId) throws NoArticleFoundException, IOException {
        deleteExistingArticleImagesIfExisting(articleId);
    }

    private void resizeAndWriteImageToFilesystem(long articleId, BufferedImage bufferedImage, Path articleImagePath , String fileExtension, int height) throws UploadedImageResolutionTooLowException, IOException {
        bufferedImage = checkImageSizeAndResize(bufferedImage, determineImageType(fileExtension), height);
        ImageIO.write(bufferedImage, PNG, new File(Paths.get(articleImagePath.toString(), getNewFileName(articleId, height)).toString()));
    }

    private void createPathIfNotExists(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    private BufferedImage checkImageSizeAndResize(BufferedImage image, int imageType, int resizeHeight) throws UploadedImageResolutionTooLowException {
        if (image.getHeight() > MIN_UPLOAD_IMAGE_HEIGHT) {
            double aspectRatio = (double) image.getWidth() / image.getHeight();
            int newImageWidth = (int) (aspectRatio * resizeHeight);

            return resizeImage(image, imageType, newImageWidth, resizeHeight);
        } else {
            throw new UploadedImageResolutionTooLowException();
        }
    }

    private BufferedImage resizeImage(BufferedImage image, int imageType, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, imageType);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, width, height, null);

        return resizedImage;
    }

    private String getNewFileName(long articleId, int height) {
        return articleId + "_" + height + "." + PNG;
    }

    private int determineImageType(String fileExtension) {
        return fileExtension.equals(PNG) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
    }

    private void checkIfArticleWithIdExits(long articleId) throws NoArticleFoundException {
        if (!articleRepository.existsById(articleId)) {
            throw new NoArticleFoundException();
        }
    }

    private void deleteExistingArticleImagesIfExisting(long articleId) throws IOException {
        File imagePathFile = new File(IMAGE_PATH);
        if (imagePathFile.listFiles() != null) {
            deleteExistingArticleImages(imagePathFile, articleId);
        }
    }

    private void deleteExistingArticleImages(File imagePathFile, long articleId) throws IOException {
        for (File file : imagePathFile.listFiles()) {
            if (file.getName().startsWith(articleId + "_")) {
                Files.delete(Paths.get(file.getPath()));
            }
        }
    }
}
