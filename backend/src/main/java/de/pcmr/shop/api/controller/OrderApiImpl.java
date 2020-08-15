package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.OrderCreationDTOOrderEntityMapper;
import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.exception.DuplicateOrderItemsException;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotEnoughArticlesOnStockException;
import de.pcmr.shop.repository.ArticleRepository;
import de.pcmr.shop.service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(OrderApiImpl.ORDER_URI)
public class OrderApiImpl {
    public static final String ORDER_URI = "/api/customer/order";

    private final OrderServiceI orderService;
    private final ArticleRepository articleRepository;

    @Autowired
    public OrderApiImpl(OrderServiceI orderService, ArticleRepository articleRepository) {
        this.orderService = orderService;
        this.articleRepository = articleRepository;
    }

    @PostMapping
    public void placeOrder(@RequestBody OrderCreationDTO orderCreationDTO, Principal principal) throws NoArticleFoundException, NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException {
        orderService.processOrder(OrderCreationDTOOrderEntityMapper.mapToOrderEntity(orderCreationDTO, articleRepository), principal);
    }
}
