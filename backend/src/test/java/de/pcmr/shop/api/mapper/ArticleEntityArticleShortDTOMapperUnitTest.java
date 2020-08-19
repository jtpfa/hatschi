package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleShortDTO;
import de.pcmr.shop.builder.ArticleEntityBuilder;
import de.pcmr.shop.domain.ArticleEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ArticleEntityArticleShortDTOMapperUnitTest {
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
    private ArticleShortDTO articleShortDTO;
    private List<ArticleShortDTO> articleShortDTOs;

    @Test
    void testMapArticleEntityToArticleShortDTO() {
        given.aArticleEntityWith(ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
        when.aArticleEntityIsMappedToArticleShortDTO(articleEntity);
        then.theAttributesOfTheArticleShortDTOAre(articleShortDTO, ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_PRICE);
    }

    @Test
    void testMapArticleEntityToArticleShortDTOWithHTML() {
        given.aArticleEntityWith(ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION_HTML, ARTICLE_DETAILS_HTML, ARTICLE_PRICE, ARTICLE_STOCK);
        when.aArticleEntityIsMappedToArticleShortDTO(articleEntity);
        then.theAttributesOfTheArticleShortDTOAreExceptHTMLFields(articleShortDTO, ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION_HTML, ARTICLE_PRICE);
    }

    @Test
    void testMapArticleEntityListToArticleShortDTOList() {
        given.aArticleEntityListWith(ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, 10);
        when.aArticleEntityListIsMappedToArticleShortDTOList(articleEntities);
        then.numberOfElementsInListAre(articleShortDTOs, 10);
        then.theAttributesOfTheElementsInTheListAre(articleEntities, articleShortDTOs);
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
        void aArticleEntityIsMappedToArticleShortDTO(ArticleEntity articleEntity) {
            articleShortDTO = ArticleEntityArticleShortDTOMapper.mapArticleEntityToArticleShortDTO(articleEntity);
        }

        void aArticleEntityListIsMappedToArticleShortDTOList(List<ArticleEntity> articleEntities) {
            articleShortDTOs = ArticleEntityArticleShortDTOMapper.mapListOfArticleEntityToListOfArticleShortDTO(articleEntities);
        }
    }

    class Then {
        void theAttributesOfTheArticleShortDTOAre(ArticleShortDTO articleShortDTO, long id, String name, String description, int price) {
            assertEquals(id, articleShortDTO.getId());
            assertEquals(name, articleShortDTO.getName());
            assertEquals(description, articleShortDTO.getDescription());
            assertEquals(price, articleShortDTO.getPrice());
        }

        void theAttributesOfTheArticleShortDTOAreExceptHTMLFields(ArticleShortDTO articleShortDTO, long id, String name, String description, int price) {
            assertEquals(id, articleShortDTO.getId());
            assertEquals(name, articleShortDTO.getName());
            assertNotEquals(description, articleShortDTO.getDescription());
            assertEquals(price, articleShortDTO.getPrice());
        }

        void theAttributesOfTheElementsInTheListAre(List<ArticleEntity> expected, List<ArticleShortDTO> actual) {
            for (int i=0; i<actual.size(); i++) {
                ArticleEntity articleEntity = expected.get(i);
                theAttributesOfTheArticleShortDTOAre(actual.get(i), articleEntity.getId(), articleEntity.getName(), articleEntity.getDescription(), articleEntity.getPrice());
            }
        }

        private <T> void numberOfElementsInListAre(List<T> list, int expected) {
            assertEquals(expected, list.size());
        }
    }
}
