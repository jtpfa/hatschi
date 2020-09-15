package de.pcmr.shop.api.controller;

import de.pcmr.shop.api.mapper.OrderMapper;
import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.api.model.OrderDTO;
import de.pcmr.shop.exception.*;
import de.pcmr.shop.repository.AddressRepository;
import de.pcmr.shop.repository.ArticleRepository;
import de.pcmr.shop.service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Implementation of OrderApi Interface.
 *
 * @author Fynn Lohse
 */

@RestController
@RequestMapping("/api")
public class OrderApiImpl implements OrderApiI {
    private final OrderServiceI orderService;
    private final ArticleRepository articleRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public OrderApiImpl(OrderServiceI orderService, ArticleRepository articleRepository, AddressRepository addressRepository) {
        this.orderService = orderService;
        this.articleRepository = articleRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public void placeOrder(@Valid OrderCreationDTO orderCreationDTO, Principal principal) throws NoArticleFoundException, NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException, NoAddressFoundException, AddressDoesNotBelongToUserException {
        orderService.processOrder(OrderMapper.mapCreationDTOToEntity(orderCreationDTO, articleRepository, addressRepository), principal);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return OrderMapper.mapListToDTOList(orderService.getAllOrders());
    }

    @Override
    public List<OrderDTO> getAllCustomerOrders(Principal principal) throws NoCustomerFoundException {
        return OrderMapper.mapListToDTOList(orderService.getAllOrdersOfCustomer(principal));
    }
}
