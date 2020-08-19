package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.ArticleCreationDTO;
import de.pcmr.shop.domain.ArticleEntity;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleCreationDTOArticleDTOMapperUnitTest {

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
    private ArticleCreationDTO articleCreationDTO;

    @Test
    void testMapArticleCreationDTOToArticleEntity() {
        given.aArticleCreationDTOWith(ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
        when.aArticleCreationDTOIsMappedTo(articleCreationDTO);
        then.theAttributesOfTheArticleEntityAre(articleEntity, ARTICLE_NAME, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
    }

    @Test
    void testMapArticleCreationDTOToArticleEntityFailHTML() {
        given.aArticleCreationDTOWith(ARTICLE_NAME_HTML, ARTICLE_DESCRIPTION, ARTICLE_DETAILS, ARTICLE_PRICE, ARTICLE_STOCK);
        assertThrows(ValidationException.class, () -> when.aArticleCreationDTOIsMappedTo(articleCreationDTO));
    }

    class Given {
        void aArticleCreationDTOWith(String name, String description, String details, int price, int stock) {
            articleCreationDTO = new ArticleCreationDTO();
            articleCreationDTO.setName(name);
            articleCreationDTO.setDescription(description);
            articleCreationDTO.setDetails(details);
            articleCreationDTO.setPrice(price);
            articleCreationDTO.setStock(stock);
        }
    }

    class When {
        void aArticleCreationDTOIsMappedTo(ArticleCreationDTO articleCreationDTO) {
            articleEntity = ArticleCreationDTOArticleEntityMapper.mapArticleCreationDTOToArticleEntity(articleCreationDTO);
        }
    }

    class Then {
        void theAttributesOfTheArticleEntityAre(ArticleEntity articleEntity, String name, String description, String details, int price, int stock) {
            assertEquals(name, articleEntity.getName());
            assertEquals(description, articleEntity.getDescription());
            assertEquals(details, articleEntity.getDetails());
            assertEquals(price, articleEntity.getPrice());
            assertEquals(stock, articleEntity.getStock());
        }
    }
}
