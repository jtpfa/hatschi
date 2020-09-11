package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.api.model.OrderDTO;
import de.pcmr.shop.exception.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/api")
public interface OrderApiI {
    String ORDER_CUSTOMER_URI = "/customer/order";
    String ORDER_EMPLOYEE_URI = "/employee/order";

    @PostMapping(ORDER_CUSTOMER_URI)
    void placeOrder(@RequestBody @Valid OrderCreationDTO orderCreationDTO, Principal principal) throws NoArticleFoundException, NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException, NoAddressFoundException, AddressDoesNotBelongToUserException;

    @GetMapping(ORDER_EMPLOYEE_URI)
    List<OrderDTO> getAllOrders();

    @GetMapping(ORDER_CUSTOMER_URI)
    List<OrderDTO> getAllCustomerOrders(Principal principal) throws NoCustomerFoundException;
}
