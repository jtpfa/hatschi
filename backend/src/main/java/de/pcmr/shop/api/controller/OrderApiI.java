package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.exception.*;

import javax.validation.Valid;
import java.security.Principal;

public interface OrderApiI {
    void placeOrder(@Valid OrderCreationDTO orderCreationDTO, Principal principal) throws NoArticleFoundException, NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException, NoAddressFoundException, AddressDoesNotBelongToUserException;
}
