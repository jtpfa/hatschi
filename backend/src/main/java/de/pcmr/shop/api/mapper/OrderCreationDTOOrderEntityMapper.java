package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.domain.OrderEntity;
import de.pcmr.shop.domain.OrderStatusEnum;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.repository.ArticleRepository;

public class OrderCreationDTOOrderEntityMapper {
    public static OrderEntity mapToOrderEntity(OrderCreationDTO orderCreationDTO, ArticleRepository articleRepository) throws NoArticleFoundException {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setInvoiceAddress(AddressDTOAddressEntityMapper.mapToAddressEntity(orderCreationDTO.getInvoiceAddress()));
        orderEntity.setShippingAddress(AddressDTOAddressEntityMapper.mapToAddressEntity(orderCreationDTO.getShippingAddress()));
        orderEntity.setOrderItems(OrderItemDTOOrderItemEntityMapper.mapListToOrderItemEntityList(orderCreationDTO.getOrderItems(), articleRepository));

        return orderEntity;
    }
}
