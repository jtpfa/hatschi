package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.OrderMapper;
import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.api.model.OrderDTO;
import de.pcmr.shop.exception.DuplicateOrderItemsException;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotEnoughArticlesOnStockException;
import de.pcmr.shop.repository.ArticleRepository;
import de.pcmr.shop.service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderApiImpl implements OrderApiI {
    public static final String ORDER_CUSTOMER_URI = "/customer/order";
    public static final String ORDER_EMPLOYEE_URI = "/employee/order";

    private final OrderServiceI orderService;
    private final ArticleRepository articleRepository;

    @Autowired
    public OrderApiImpl(OrderServiceI orderService, ArticleRepository articleRepository) {
        this.orderService = orderService;
        this.articleRepository = articleRepository;
    }

    @PostMapping(ORDER_CUSTOMER_URI)
    public void placeOrder(@RequestBody @Valid OrderCreationDTO orderCreationDTO, Principal principal) throws NoArticleFoundException, NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException {
        orderService.processOrder(OrderMapper.mapCreationDTOToEntity(orderCreationDTO, articleRepository), principal);
    }

    @GetMapping(ORDER_EMPLOYEE_URI)
    public List<OrderDTO> getAllOrders() {
        return OrderMapper.mapListToDTOList(orderService.getAllOrders());
    }

    @GetMapping(ORDER_CUSTOMER_URI)
    public List<OrderDTO> getAllCustomerOrders(Principal principal) throws NoCustomerFoundException {
        return OrderMapper.mapListToDTOList(orderService.getAllOrdersOfCustomer(principal));
    }
}
