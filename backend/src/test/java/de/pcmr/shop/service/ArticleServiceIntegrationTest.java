package de.pcmr.shop.service;

import de.pcmr.shop.AbstractIntegrationTest;
import de.pcmr.shop.builder.ArticleEntityBuilder;
import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.UploadedImageResolutionTooLowException;
import de.pcmr.shop.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Partial integration test for the article service.
 *
 * @author Fynn Lohse
 */

class ArticleServiceIntegrationTest extends AbstractIntegrationTest {

    private static final String ARTICLE_TITLE_1 = "Testartikel 1";
    private static final String ARTICLE_TITLE_2 = "Testartikel 2";
    private static final String ARTICLE_TITLE_3 = "Testartikel 3";
    private static final String ARTICLE_DESCRIPTION = "Tolle Produktbeschreibung";
    private static final String ARTICLE_DETAILS = "Tolle Produktdetails";

    private final ArticleServiceI articleService;
    private final ArticleRepository articleRepository;

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    private List<ArticleEntity> resultArticlesEntities;
    private ArticleEntity lastSavedArticleEntity;
    private List<ArticleEntity> articleEntities = new ArrayList<>();
    private ArticleEntity updatedArticleEntity;

    private MultipartFile nullImageFile;
    private MultipartFile imageFile;

    @Autowired
    ArticleServiceIntegrationTest(ArticleServiceI articleService, ArticleRepository articleRepository) {
        super();
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }

    @BeforeEach
    void cleanUpArticleEntityList() {
        articleEntities = new ArrayList<>();
    }

    @Test
    void testGetAllArticles() {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 15);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_2, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 1000, 10);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_3, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 200, 32);
        when.allArticlesAreRetrieved();
        then.numberOfArticlesInResultListAre(3);
    }

    @Test
    void testGetArticle() throws NoArticleFoundException {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 15);
        when.aArticleEntityIsRetirieved(lastSavedArticleEntity.getId());
        then.numberOfArticlesInDatabaseAre(1);
        then.theArticleInDatabaseEquals(lastSavedArticleEntity);
    }

    @Test
    void testGetArticleWrongId() {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 15);
        assertThrows(NoArticleFoundException.class, () -> when.aArticleEntityIsRetirieved(lastSavedArticleEntity.getId()+1));
    }

    @Test
    void testCreateNewArticle() throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException {
        given.aArticleEntityWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 15);
        given.aArticleEntityWith(ARTICLE_TITLE_2, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 1000, 5);
        given.aArticleEntityWith(ARTICLE_TITLE_3, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 70000, 3);
        when.newArticlesEntityAreCreated(articleEntities);
        then.numberOfArticlesInDatabaseAre(3);
    }

    @Test
    void testCreateNewArticleInvalidName() {
        given.aArticleEntityWith("ab", ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 15);
        assertThrows(ConstraintViolationException.class, () -> when.newArticlesEntityAreCreated(articleEntities));
    }

    @Test
    void testCreateNewArticleInvalidPrice() {
        given.aArticleEntityWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, -1, 15);
        assertThrows(ConstraintViolationException.class, () -> when.newArticlesEntityAreCreated(articleEntities));
    }

    @Test
    void testCreateNewArticleInvalidStock() {
        given.aArticleEntityWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, -1);
        assertThrows(ConstraintViolationException.class, () -> when.newArticlesEntityAreCreated(articleEntities));
    }

    @Test
    void testCreateNewArticleInvalidDescription() {
        given.aArticleEntityWith(ARTICLE_TITLE_1, "bla", ARTICLE_DETAILS, 500, 5);
        assertThrows(ConstraintViolationException.class, () -> when.newArticlesEntityAreCreated(articleEntities));
    }

    @Test
    void testCreateNewArticleInvalidDetails() {
        given.aArticleEntityWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, "bla", 500, 5);
        assertThrows(ConstraintViolationException.class, () -> when.newArticlesEntityAreCreated(articleEntities));
    }

    @Test
    void testUpdateArticle() throws NoArticleFoundException, IOException, UploadedImageResolutionTooLowException {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 5);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_2, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 5000, 10);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_3, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 70000, 2);
        given.aUpdatedArticleEntityWith(articleEntities.get(1).getId(), "Testartikel 2 Neu", ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 1000, 15);
        when.aArticleIsUpdated(updatedArticleEntity);
        then.articleEntityIsUpdated(articleEntities.get(1).getId(), updatedArticleEntity);
        then.numberOfArticlesInDatabaseAre(3);
    }

    @Test
    void testUpdateArticleWrongId() {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 5);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_2, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 5000, 10);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_3, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 70000, 2);
        given.aUpdatedArticleEntityWith(articleEntities.get(1).getId() + 10, "Testartikel 2 Neu", ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 1000, 15);
        assertThrows(NoArticleFoundException.class, () -> when.aArticleIsUpdated(updatedArticleEntity));
    }

    @Test
    void testDeleteArticle() throws NoArticleFoundException, IOException {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 5);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_2, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 5000, 10);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_3, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 70000, 2);
        when.aArticleIsDeleted(articleEntities.get(1).getId());
        then.numberOfArticlesInDatabaseAre(2);
        then.articleWithIdDoesNotExist(articleEntities.get(1).getId());
    }

    @Test
    void testDeleteArticleWrongId() {
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_1, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 500, 5);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_2, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 5000, 10);
        given.aArticleEntityInDatabaseWith(ARTICLE_TITLE_3, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 70000, 2);
        assertThrows(NoArticleFoundException.class, () -> when.aArticleIsDeleted(articleEntities.get(1).getId() + 10));
    }

    class Given {
        void aArticleEntityInDatabaseWith(String name, String description, String details, int price, int stock) {
            ArticleEntity articleEntity = ArticleEntityBuilder.anArticleEntity()
                    .withName(name)
                    .withDescription(description)
                    .withDetails(details)
                    .withPrice(price)
                    .withStock(stock)
                    .build();

            lastSavedArticleEntity = articleRepository.save(articleEntity);
            articleEntities.add(lastSavedArticleEntity);
        }

        void aArticleEntityWith(String name, String description, String details, int price, int stock) {
            ArticleEntity articleEntity = ArticleEntityBuilder.anArticleEntity()
                    .withName(name)
                    .withDescription(description)
                    .withDetails(details)
                    .withPrice(price)
                    .withStock(stock)
                    .build();

            articleEntities.add(articleEntity);
        }

        void aUpdatedArticleEntityWith(long id, String name, String description, String details, int price, int stock) {
            updatedArticleEntity = new ArticleEntity();
            updatedArticleEntity.setId(id);
            updatedArticleEntity.setName(name);
            updatedArticleEntity.setDescription(description);
            updatedArticleEntity.setDetails(details);
            updatedArticleEntity.setPrice(price);
            updatedArticleEntity.setStock(stock);
        }
    }

    class When {
        void allArticlesAreRetrieved() {
            resultArticlesEntities = articleService.getAllArticles();
        }

        void aArticleEntityIsRetirieved(long id) throws NoArticleFoundException {
            articleService.getArticle(id);
        }

        void newArticlesEntityAreCreated(List<ArticleEntity> articleEntities) throws NoArticleFoundException, UploadedImageResolutionTooLowException, IOException {
            for (ArticleEntity articleEntity : articleEntities) {
                articleService.createNewArticle(articleEntity, nullImageFile);
            }
        }

        void aArticleIsUpdated(ArticleEntity articleEntity) throws NoArticleFoundException, IOException, UploadedImageResolutionTooLowException {
            articleService.updateArticle(articleEntity, nullImageFile);
        }

        void aArticleIsDeleted(long id) throws NoArticleFoundException, IOException {
            articleService.deleteArticle(id);
        }
    }

    class Then {
        void numberOfArticlesInResultListAre(int number) {
            assertEquals(number, resultArticlesEntities.size());
        }

        void numberOfArticlesInDatabaseAre(int numberOfArticles) {
            assertEquals(numberOfArticles, articleRepository.findAll().size());
        }

        void theArticleInDatabaseEquals(ArticleEntity articleEntity) {
            ArticleEntity dbArticleEntity = articleRepository.findAll().get(0);
            articleEntityEquals(articleEntity, dbArticleEntity);
        }

        void articleEntityIsUpdated(long id, ArticleEntity updatedArticleEntity) {
            ArticleEntity articleEntity = articleRepository.findById(id).get();
            articleEntityEquals(updatedArticleEntity, articleEntity);
        }

        void articleWithIdDoesNotExist(long id) {
            assertFalse(articleRepository.existsById(id));
        }

        private void articleEntityEquals(ArticleEntity expected, ArticleEntity actual) {
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getDescription(), actual.getDescription());
            assertEquals(expected.getDetails(), actual.getDetails());
            assertEquals(expected.getPrice(), actual.getPrice());
            assertEquals(expected.getStock(), actual.getStock());
        }
    }
}
