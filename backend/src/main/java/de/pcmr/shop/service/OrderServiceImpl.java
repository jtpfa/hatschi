package de.pcmr.shop.service;

import de.pcmr.shop.domain.*;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.DuplicateOrderItemsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotEnoughArticlesOnStockException;
import de.pcmr.shop.repository.AddressRepository;
import de.pcmr.shop.repository.ArticleRepository;
import de.pcmr.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Service
@Validated
public class OrderServiceImpl implements OrderServiceI {

    private final ArticleRepository articleRepository;
    private final OrderRepository orderRepository;
    private final CustomerServiceI customerService;
    private final AddressRepository addressRepository;

    @Autowired
    public OrderServiceImpl(ArticleRepository articleRepository, OrderRepository orderRepository, CustomerServiceI customerService, AddressRepository addressRepository) {
        this.articleRepository = articleRepository;
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.addressRepository = addressRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void processOrder(@Valid OrderEntity orderEntity, Principal principal) throws NotEnoughArticlesOnStockException, DuplicateOrderItemsException, NoCustomerFoundException, AddressDoesNotBelongToUserException {
        validateNoDuplicateItems(orderEntity);
        checkAndReduceStockOfOrder(orderEntity);
        calculateOrderItemPrices(orderEntity);

        CustomerEntity currentCustomer = customerService.getCurrentCustomer(principal);
        checkIfAddressBelongsToUser(orderEntity, currentCustomer);

        orderEntity.setCustomer(currentCustomer);
        orderEntity.getInvoiceAddress().setCustomer(currentCustomer);
        orderEntity.getShippingAddress().setCustomer(currentCustomer);
        orderEntity.setOrderDate(new Date());
        orderEntity.setPaid(false);
        orderEntity.setOrderStatus(OrderStatusEnum.OPEN);
        orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderEntity> getAllOrdersOfCustomer(Principal principal) throws NoCustomerFoundException {
        CustomerEntity customerEntity = customerService.getCurrentCustomer(principal);
        return orderRepository.findByCustomer(customerEntity);
    }

    private void checkAndReduceStockOfOrder(OrderEntity orderEntity) throws NotEnoughArticlesOnStockException {
        for (OrderItemEntity orderItemEntity : orderEntity.getOrderItems()) {
            checkStockOfOrderItem(orderItemEntity);
            reduceStockOfOrderItemArticle(orderItemEntity);
        }
    }

    private void calculateOrderItemPrices(OrderEntity orderEntity) {
        orderEntity.getOrderItems().forEach(e -> e.setPrice(e.getQuantity() * e.getArticle().getPrice()));
    }

    private void checkStockOfOrderItem(OrderItemEntity orderItemEntity) throws NotEnoughArticlesOnStockException {
        ArticleEntity articleEntity = orderItemEntity.getArticle();
        if (articleEntity.getStock() < orderItemEntity.getQuantity()) {
            throw new NotEnoughArticlesOnStockException();
        }
    }

    private void reduceStockOfOrderItemArticle(OrderItemEntity orderItemEntity) {
        ArticleEntity articleEntity = orderItemEntity.getArticle();
        articleEntity.setStock(articleEntity.getStock() - orderItemEntity.getQuantity());
        articleRepository.save(articleEntity);
    }

    private void validateNoDuplicateItems(OrderEntity orderEntity) throws DuplicateOrderItemsException {
        List<ArticleEntity> articleEntities = new ArrayList<>();
        for (OrderItemEntity orderItemEntity : orderEntity.getOrderItems()) {
            articleEntities.add(orderItemEntity.getArticle());
        }

        Set<ArticleEntity> articleEntitySet = new HashSet<>(articleEntities);
        if (articleEntitySet.size() < articleEntities.size()) {
            throw new DuplicateOrderItemsException();
        }
    }

    private void checkIfAddressBelongsToUser(OrderEntity orderEntity, CustomerEntity currentCustomer) throws AddressDoesNotBelongToUserException {
        if (!orderEntity.getShippingAddress().getCustomer().getId().equals(currentCustomer.getId())
                || !orderEntity.getInvoiceAddress().getCustomer().getId().equals(currentCustomer.getId())) {
            throw new AddressDoesNotBelongToUserException();
        }
    }
}
