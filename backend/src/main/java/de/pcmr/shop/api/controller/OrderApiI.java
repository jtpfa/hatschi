package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.api.model.OrderDTO;
import de.pcmr.shop.exception.DuplicateOrderItemsException;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotEnoughArticlesOnStockException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

public interface OrderApiI {
    void placeOrder(@Valid OrderCreationDTO orderCreationDTO, Principal principal) throws NoArticleFoundException, NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException;
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getAllCustomerOrders(Principal principal) throws NoCustomerFoundException;
}
