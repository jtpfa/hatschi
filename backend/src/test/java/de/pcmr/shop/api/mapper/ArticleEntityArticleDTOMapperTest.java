package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.builder.ArticleEntityBuilder;
import de.pcmr.shop.domain.ArticleEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ArticleEntityArticleDTOMapperTest {
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
    private ArticleDTO articleDTO;

    @Test
    public void mapArticleEntityToArticleDTO() {
        given.aArticleEntityWith(ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
        when.aArticleEntityIsMappedToArticleDTO(articleEntity);
        then.theAttributesOfTheArticleDTOAre(articleDTO, ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
    }

    @Test
    public void mapArticleEntityToArticleDTOWithHTML() {
        given.aArticleEntityWith(ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION_HTML, ARTICLE_DETAILS_HTML, ARTICLE_PRICE, ARTICLE_STOCK);
        when.aArticleEntityIsMappedToArticleDTO(articleEntity);
        then.theAttributesOfTheArticleDTOAreExceptHTMLFields(articleDTO, ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION_HTML, ARTICLE_DETAILS_HTML, ARTICLE_PRICE, ARTICLE_STOCK);
    }

    class Given {
        public void aArticleEntityWith(long id, String name, String description, String details, int price, int stock) {
            articleEntity = ArticleEntityBuilder.anArticleEntity()
                    .withId(id)
                    .withName(name)
                    .withDescription(description)
                    .withDetails(details)
                    .withPrice(price)
                    .withStock(stock)
                    .build();
        }
    }

    class When {
        public void aArticleEntityIsMappedToArticleDTO(ArticleEntity articleEntity) {
            articleDTO = ArticleEntityArticleDTOMapper.mapArticleEntityToArticleDTO(articleEntity);
        }
    }

    class Then {
        public void theAttributesOfTheArticleDTOAre(ArticleDTO articleDTO, long id, String name, String description, String details, int price, int stock) {
            assertEquals(id, articleDTO.getId());
            assertEquals(name, articleDTO.getName());
            assertEquals(description, articleDTO.getDescription());
            assertEquals(details, articleDTO.getDetails());
            assertEquals(price, articleDTO.getPrice());
            assertEquals(stock, articleDTO.getStock());
        }

        public void theAttributesOfTheArticleDTOAreExceptHTMLFields(ArticleDTO articleDTO, long id, String name, String description, String details, int price, int stock) {
            assertEquals(id, articleDTO.getId());
            assertEquals(name, articleDTO.getName());
            assertNotEquals(description, articleDTO.getDescription());
            assertNotEquals(details, articleDTO.getDetails());
            assertEquals(price, articleDTO.getPrice());
            assertEquals(stock, articleDTO.getStock());
        }
    }
}
