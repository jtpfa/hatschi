package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.api.model.OrderDTO;
import de.pcmr.shop.exception.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

public interface OrderApiI {
    void placeOrder(@Valid OrderCreationDTO orderCreationDTO, Principal principal) throws NoArticleFoundException, NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException, NoAddressFoundException, AddressDoesNotBelongToUserException;
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getAllCustomerOrders(Principal principal) throws NoCustomerFoundException;
}
