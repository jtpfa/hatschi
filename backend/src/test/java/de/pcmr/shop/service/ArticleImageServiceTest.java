package de.pcmr.shop.service;

import de.pcmr.shop.builder.ArticleEntityBuilder;
import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import de.pcmr.shop.repository.ArticleRepository;
import de.pcmr.shop.repository.CustomerRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArticleImageServiceTest extends AbstractServiceTest {

    private static final String IMAGE_PATH = "./media/article/";
    private static final String TEST_IMAGE_PATH_PNG = "./src/test/resources/images/gpu.png";
    private static final String TEST_IMAGE_SMALL_PATH_PNG = "./src/test/resources/images/gpu_small.png";

    private static final String TEST_IMAGE_PATH_JPG = "./src/test/resources/images/gpu.jpg";

    private static final String ARTICLE_TITLE_ = "Testartikel 1";
    private static final String ARTICLE_DESCRIPTION = "Tolle Produktbeschreibung";
    private static final String ARTICLE_DETAILS = "Tolle Produktdetails";

    private final ArticleImageServiceI articleImageService;
    private final ArticleRepository articleRepository;

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    private ArticleEntity lastSavedArticleEntity;

    @Autowired
    public ArticleImageServiceTest(Environment environment, CustomerRepository customerRepository, ArticleRepository articleRepository, ArticleImageServiceI articleImageService) {
        super(environment, customerRepository, articleRepository);
        this.articleImageService = articleImageService;
        this.articleRepository = articleRepository;
    }

    @BeforeEach
    public void cleanUpMedia() {
        File imageDir = new File(IMAGE_PATH);

        if (imageDir.listFiles() != null) {
            for (File file : imageDir.listFiles()) {
                file.delete();
            }
            imageDir.delete();
        }
    }

    @Test
    public void testProcessAndSaveArticleImagePng() throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 5);
        when.aImageIsProcessed(lastSavedArticleEntity.getId(), TEST_IMAGE_PATH_PNG);
        then.numberOfFilesInArticleImageFolderAre(3);
        then.theImageHasBeenRescaled();
    }

    @Test
    public void testProcessAndSaveArticleImageArticleDoesNotExist() {
        assertThrows(NoArticleFoundException.class, () -> when.aImageIsProcessed(5, TEST_IMAGE_PATH_PNG));
    }

    @Test
    public void testProcessAndSaveArticleImageTooSmall() {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 5);
        assertThrows(UploadedImageResolutionTooLowException.class, () -> when.aImageIsProcessed(lastSavedArticleEntity.getId(), TEST_IMAGE_SMALL_PATH_PNG));
    }

    @Test
    public void testProcessAndSaveArticleImageAlreadyExists() throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 5);
        when.aImageIsProcessed(lastSavedArticleEntity.getId(), TEST_IMAGE_PATH_PNG);
        when.aImageIsProcessed(lastSavedArticleEntity.getId(), TEST_IMAGE_PATH_PNG);
        then.numberOfFilesInArticleImageFolderAre(3);
        then.theImageHasBeenRescaled();
    }

    @Test
    public void testProcessAndSaveArticleImageJpg() throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 5);
        when.aImageIsProcessed(lastSavedArticleEntity.getId(), TEST_IMAGE_PATH_JPG);
        then.numberOfFilesInArticleImageFolderAre(3);
        then.theImageHasBeenRescaled();
    }

    class Given {
        public void aArticleEntityInDatabaseWith(String name, String description, String details, int price, int stock) {
            ArticleEntity articleEntity = ArticleEntityBuilder.anArticleEntity()
                    .withName(name)
                    .withDescription(description)
                    .withDetails(details)
                    .withPrice(price)
                    .withStock(stock)
                    .build();

            lastSavedArticleEntity = articleRepository.save(articleEntity);
        }
    }

    class When {
        public void aImageIsProcessed(long articleId, String filePath) throws IOException, NoArticleFoundException, UploadedImageResolutionTooLowException {
            File imageFile = new File(filePath);
            MultipartFile multipartFile = new MockMultipartFile(FilenameUtils.getName(filePath), FileUtils.readFileToByteArray(imageFile));

            articleImageService.processAndSaveArticleImage(articleId, multipartFile);
        }
    }

    class Then {
        public void numberOfFilesInArticleImageFolderAre(int numberOfFiles) {
            assertEquals(numberOfFiles, new File(IMAGE_PATH).list().length);
        }

        public void theImageHasBeenRescaled() throws IOException {
            for (File file : new File(IMAGE_PATH).listFiles()) {
                String resolutionString = FilenameUtils.getBaseName(file.getName()).split("_")[1];
                checkImageHeight(file, Integer.valueOf(resolutionString));
            }
        }

        private void checkImageHeight(File imageFile, int expectedHeight) throws IOException {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            assertEquals(expectedHeight, bufferedImage.getHeight());
        }
    }
}
