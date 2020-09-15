package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleDTO;
import de.pcmr.shop.domain.ArticleEntity;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Fynn Lohse
 */

class ArticleDTOArticleEntityMapperUnitTest {
    private static final long ARTICLE_ID = 207L;
    private static final String ARTICLE_NAME = "Testartikel";
    private static final String ARTICLE_NAME_HTML = "<script>Testartikel</script>";
    private static final String ARTICLE_DESCRIPTION = "Artikelbeschreibung";
    private static final String ARTICLE_DETAILS = "Artikeldetails";
    private static final int ARTICLE_PRICE = 5000;
    private static final int ARTICLE_STOCK = 13;

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    private ArticleEntity articleEntity;
    private ArticleDTO articleDTO;

    @Test
    void testMapArticleCreationDTOToArticleEntity() {
        given.aArticleDTOWith(ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
        when.aArticleDTOIsMappedTo(articleDTO);
        then.theAttributesOfTheArticleEntityAre(articleEntity, ARTICLE_ID, ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
    }

    @Test
    void testMapArticleCreationDTOToArticleEntityFailHTML() {
        given.aArticleDTOWith(ARTICLE_ID, ARTICLE_NAME_HTML, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
        assertThrows(ValidationException.class, () -> when.aArticleDTOIsMappedTo(articleDTO));
    }

    class Given {
        void aArticleDTOWith(long id, String name, String description, String details, int price, int stock) {
            articleDTO = new ArticleDTO();
            articleDTO.setId(id);
            articleDTO.setName(name);
            articleDTO.setDescription(description);
            articleDTO.setDetails(details);
            articleDTO.setPrice(price);
            articleDTO.setStock(stock);
        }
    }

    class When {
        void aArticleDTOIsMappedTo(ArticleDTO articleDTO) {
            articleEntity = ArticleMapper.mapArticleDTOToArticleEntity(articleDTO);
        }
    }

    class Then {
        void theAttributesOfTheArticleEntityAre(ArticleEntity articleEntity, long id, String name, String description, String details, int price, int stock) {
            assertEquals(id, articleEntity.getId());
            assertEquals(name, articleEntity.getName());
            assertEquals(description, articleEntity.getDescription());
            assertEquals(details, articleEntity.getDetails());
            assertEquals(price, articleEntity.getPrice());
            assertEquals(stock, articleEntity.getStock());
        }
    }
}
