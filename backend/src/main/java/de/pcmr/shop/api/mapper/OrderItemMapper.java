package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.OrderItemDTO;
import de.pcmr.shop.domain.OrderItemEntity;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for mapping DTOs from or to OrderItemEntities
 *
 * @author Fynn Lohse
 */

public class OrderItemMapper {
    private OrderItemMapper() {
        throw new IllegalStateException();
    }

    /**
     * Method maps List of OrderItemDTOs to List of OrderItemEntities.
     *
     * @param orderItemDTOs List of OrderItemDTOs
     * @param articleRepository  ArticleRepository
     * @return List of OrderItemEntities
     * @throws NoArticleFoundException if no article was found to given ID
     */

    public static List<OrderItemEntity> mapListToOrderItemEntityList(List<OrderItemDTO> orderItemDTOs, ArticleRepository articleRepository) throws NoArticleFoundException {
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            orderItemEntities.add(mapToOrderItemEntity(orderItemDTO, articleRepository));
        }
        return orderItemEntities;
    }

    private static OrderItemEntity mapToOrderItemEntity(OrderItemDTO orderItemDTO, ArticleRepository articleRepository) throws NoArticleFoundException {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        if (articleRepository.existsById(orderItemDTO.getArticleId())) {
            orderItemEntity.setArticle(articleRepository.findById(orderItemDTO.getArticleId()).get());
        } else {
            throw new NoArticleFoundException();
        }
        orderItemEntity.setQuantity(orderItemDTO.getQuantity());
        return orderItemEntity;
    }
}
