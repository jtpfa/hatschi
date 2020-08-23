package de.pcmr.shop.service;

import de.pcmr.shop.domain.OrderEntity;
import de.pcmr.shop.exception.DuplicateOrderItemsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotEnoughArticlesOnStockException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

public interface OrderServiceI {
    void processOrder(@Valid OrderEntity orderEntity, Principal principal) throws NotEnoughArticlesOnStockException, DuplicateOrderItemsException, NoCustomerFoundException;
    List<OrderEntity> getAllOrders();
    List<OrderEntity> getAllOrdersOfCustomer(Principal principal) throws NoCustomerFoundException;
}
