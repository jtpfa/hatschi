package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.builder.ArticleEntityBuilder;
import de.pcmr.shop.domain.ArticleEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ArticleEntityArticleDTOMapperUnitTest {
    private static final long ARTICLE_ID = 15L;
    private static final String ARTICLE_NAME = "Testartikel";
    private static final String ARTICLE_DESCRIPTION = "Artikelbeschreibung";
    private static final String ARTICLE_DESCRIPTION_HTML = "<script>Artikelbeschreibung</script>";
    private static final String ARTICLE_DETAILS = "Artikeldetails";
    private static final String ARTICLE_DETAILS_HTML = "<script>Artikeldetails</script>";
    private static final int ARTICLE_PRICE = 5000;
    private static final int ARTICLE_STOCK = 13;

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    private ArticleEntity articleEntity;
    private List<ArticleEntity> articleEntities = new ArrayList<>();
    private ArticleDTO articleDTO;
    private List<ArticleDTO> articleDTOs;

    @Test
    void testMapArticleEntityToArticleDTO() {
        given.aArticleEntityWith(ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
        when.aArticleEntityIsMappedToArticleDTO(articleEntity);
        then.theAttributesOfTheArticleDTOAre(articleDTO, ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
    }

    @Test
    void testMapArticleEntityToArticleDTOWithHTML() {
        given.aArticleEntityWith(ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION_HTML, ARTICLE_DETAILS_HTML, ARTICLE_PRICE, ARTICLE_STOCK);
        when.aArticleEntityIsMappedToArticleDTO(articleEntity);
        then.theAttributesOfTheArticleDTOAreExceptHTMLFields(articleDTO, ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION_HTML, ARTICLE_DETAILS_HTML, ARTICLE_PRICE, ARTICLE_STOCK);
    }

    @Test
    void testMapListOfArticleEntitesToListOfArticleDTOs() {
        given.aArticleEntityListWith(ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 10);
        when.aListOfArticleEntitiesIsMappedToAListOfArticleDTOs(articleEntities);
        then.numberOfElementsInListAre(articleDTOs, 10);
        then.theAttributesOfTheElementsInTheListAre(articleEntities, articleDTOs);
    }

    class Given {
        void aArticleEntityWith(long id, String name, String description, String details, int price, int stock) {
            articleEntity = ArticleEntityBuilder.anArticleEntity()
                    .withId(id)
                    .withName(name)
                    .withDescription(description)
                    .withDetails(details)
                    .withPrice(price)
                    .withStock(stock)
                    .build();
        }

        void aArticleEntityListWith(String name, String description, String details, int length) {
            for (int i=0; i<length; i++) {
                articleEntities.add(ArticleEntityBuilder.anArticleEntity()
                        .withId(i+1)
                        .withName(name)
                        .withDescription(description)
                        .withDetails(details)
                        .withPrice(new Random().nextInt(20000))
                        .withStock(new Random().nextInt(200))
                        .build());
            }
        }
    }

    class When {
        void aArticleEntityIsMappedToArticleDTO(ArticleEntity articleEntity) {
            articleDTO = ArticleEntityArticleDTOMapper.mapArticleEntityToArticleDTO(articleEntity);
        }

        void aListOfArticleEntitiesIsMappedToAListOfArticleDTOs(List<ArticleEntity> articleEntities) {
            articleDTOs = ArticleEntityArticleDTOMapper.mapListOfArticleEntitiesToListOfArticleDTO(articleEntities);
        }
    }

    class Then {
        void theAttributesOfTheArticleDTOAre(ArticleDTO articleDTO, long id, String name, String description, String details, int price, int stock) {
            assertEquals(id, articleDTO.getId());
            assertEquals(name, articleDTO.getName());
            assertEquals(description, articleDTO.getDescription());
            assertEquals(details, articleDTO.getDetails());
            assertEquals(price, articleDTO.getPrice());
            assertEquals(stock, articleDTO.getStock());
        }

        void theAttributesOfTheArticleDTOAreExceptHTMLFields(ArticleDTO articleDTO, long id, String name, String description, String details, int price, int stock) {
            assertEquals(id, articleDTO.getId());
            assertEquals(name, articleDTO.getName());
            assertNotEquals(description, articleDTO.getDescription());
            assertNotEquals(details, articleDTO.getDetails());
            assertEquals(price, articleDTO.getPrice());
            assertEquals(stock, articleDTO.getStock());
        }

        private <T> void numberOfElementsInListAre(List<T> list, int expected) {
            assertEquals(expected, list.size());
        }

        void theAttributesOfTheElementsInTheListAre(List<ArticleEntity> expected, List<ArticleDTO> actual) {
            for (int i=0; i<actual.size(); i++) {
                ArticleEntity articleEntity = expected.get(i);
                theAttributesOfTheArticleDTOAre(actual.get(i), articleEntity.getId(), articleEntity.getName(), articleEntity.getDescription(), articleEntity.getDetails(), articleEntity.getPrice(), articleEntity.getStock());
            }
        }
    }
}
