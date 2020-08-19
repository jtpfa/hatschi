package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.OrderItemDTO;
import de.pcmr.shop.domain.OrderItemEntity;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderItemDTOOrderItemEntityMapper {
    public static List<OrderItemEntity> mapListToOrderItemEntityList(List<OrderItemDTO> orderItemDTOs, ArticleRepository articleRepository) throws NoArticleFoundException {
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            orderItemEntities.add(mapToOrderItemEntity(orderItemDTO, articleRepository));
        }
        return orderItemEntities;
    }


    private static OrderItemEntity mapToOrderItemEntity(OrderItemDTO orderItemDTO, ArticleRepository articleRepository) throws NoArticleFoundException {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        if (articleRepository.existsById(orderItemDTO.getArticle())) {
            orderItemEntity.setArticle(articleRepository.findById(orderItemDTO.getArticle()).get());
        } else {
            throw new NoArticleFoundException();
        }
        orderItemEntity.setQuantity(orderItemDTO.getQuantity());
        return orderItemEntity;
    }
}
