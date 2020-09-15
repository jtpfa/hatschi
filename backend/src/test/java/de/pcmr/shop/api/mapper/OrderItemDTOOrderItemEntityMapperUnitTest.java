package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.OrderItemDTO;
import de.pcmr.shop.builder.ArticleEntityBuilder;
import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.domain.OrderItemEntity;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Fynn Lohse
 */

@ExtendWith(MockitoExtension.class)
public class OrderItemDTOOrderItemEntityMapperUnitTest {

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    private List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
    private List<OrderItemEntity> orderItemEntities;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    void testMapToOrderItemEntity() throws NoArticleFoundException {
        given.aDatabaseMockConfig();
        given.anOrderItemDto(1L, 3);
        given.anOrderItemDto(2L, 6);
        when.anOrderItemDTOIsMappedToOrderItemEntity(orderItemDTOs);
        then.theListSizeIs(orderItemEntities, orderItemDTOs.size());
        then.theAttributesOfTheOrderItemsMatch(orderItemEntities, orderItemDTOs);
        then.theOtherAttributesAreNotNull(orderItemEntities);
    }

    class Given {
        void aDatabaseMockConfig() {
            ArticleEntity articleEntity1 = ArticleEntityBuilder.anArticleEntity()
                    .withId(1L)
                    .withName("Testartikel 1")
                    .withDescription("Beschreibung 1")
                    .withDetails("Details 1")
                    .withPrice(9999)
                    .withStock(5)
                    .build();

            ArticleEntity articleEntity2 = ArticleEntityBuilder.anArticleEntity()
                    .withId(2L)
                    .withName("Testartikel 2")
                    .withDescription("Beschreibung 2")
                    .withDetails("Details 2")
                    .withPrice(5999)
                    .withStock(10)
                    .build();

            Mockito.when(articleRepository.existsById(Mockito.anyLong())).thenReturn(true);
            Mockito.when(articleRepository.findById(1L)).thenReturn(Optional.of(articleEntity1));
            Mockito.when(articleRepository.findById(2L)).thenReturn(Optional.of(articleEntity2));
        }

        void anOrderItemDto(long articleId, int quantity) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setArticleId(articleId);
            orderItemDTO.setQuantity(quantity);
            orderItemDTOs.add(orderItemDTO);
        }
    }

    class When {
        void anOrderItemDTOIsMappedToOrderItemEntity(List<OrderItemDTO> orderItemDTOs) throws NoArticleFoundException {
            orderItemEntities = OrderItemMapper.mapListToOrderItemEntityList(orderItemDTOs, articleRepository);
        }
    }

    class Then {
        void theListSizeIs(List<OrderItemEntity> orderItemEntities, int expected) {
            assertEquals(expected, orderItemEntities.size());
        }

        void theAttributesOfTheOrderItemsMatch(List<OrderItemEntity> orderItemEntities, List<OrderItemDTO> orderItemDTOs) {
            for (int i=0; i < orderItemEntities.size(); i++) {
                assertEquals(orderItemDTOs.get(i).getArticleId(), orderItemEntities.get(i).getArticle().getId());
                assertEquals(orderItemDTOs.get(i).getQuantity(), orderItemEntities.get(i).getQuantity());
            }
        }

        void theOtherAttributesAreNotNull(List<OrderItemEntity> orderItemEntities) {
            for (OrderItemEntity orderItemEntity : orderItemEntities) {
                ArticleEntity orderItemArticleEntity = orderItemEntity.getArticle();
                assertNotNull(orderItemArticleEntity.getId());
                assertNotNull(orderItemArticleEntity.getDescription());
                assertNotNull(orderItemArticleEntity.getName());
                assertNotNull(orderItemArticleEntity.getDetails());
                assertNotNull(orderItemArticleEntity.getStock());
                assertNotNull(orderItemArticleEntity.getPrice());
            }
        }
    }
}
