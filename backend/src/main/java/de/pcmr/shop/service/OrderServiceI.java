package de.pcmr.shop.service;

import de.pcmr.shop.domain.OrderEntity;
import de.pcmr.shop.exception.DuplicateOrderItemsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotEnoughArticlesOnStockException;

import javax.validation.Valid;
import java.security.Principal;

public interface OrderServiceI {
    void processOrder(@Valid OrderEntity orderEntity, Principal principal) throws NotEnoughArticlesOnStockException, DuplicateOrderItemsException, NoCustomerFoundException;
}
