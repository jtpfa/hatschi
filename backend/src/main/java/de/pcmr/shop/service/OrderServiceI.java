package de.pcmr.shop.service;

import de.pcmr.shop.domain.OrderEntity;
import de.pcmr.shop.exception.AddressDoesNotBelongToUserException;
import de.pcmr.shop.exception.DuplicateOrderItemsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotEnoughArticlesOnStockException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Interface of the order service.
 * Orders can be processed an listed.
 *
 * @author Fynn Lohse
 */

public interface OrderServiceI {
    /**
     * Method processes new order. It validates that there are no duplicates, checks the stock, calculates the prices and finally saves the order.
     *
     * @param orderEntity order entity
     * @param principal principal of spring security context
     * @throws NotEnoughArticlesOnStockException if not enough article are on stock
     * @throws DuplicateOrderItemsException if there are duplicate order items referring to the same article
     * @throws NoCustomerFoundException if no customer could be determined by the principal
     * @throws AddressDoesNotBelongToUserException if the provided address is not owned by the current customer
     */

    void processOrder(@Valid OrderEntity orderEntity, Principal principal) throws NotEnoughArticlesOnStockException, DuplicateOrderItemsException, NoCustomerFoundException, AddressDoesNotBelongToUserException;

    /**
     * Method returns all orders entities of all customers.
     *
     * @return list of all orders.
     */

    List<OrderEntity> getAllOrders();

    /**
     * Method return all orders placed by the current customer.
     *
     * @param principal principal of spring security context
     * @return list of all orders placed by the current customer
     * @throws NoCustomerFoundException if no customer could be determined by the principal
     */

    List<OrderEntity> getAllOrdersOfCustomer(Principal principal) throws NoCustomerFoundException;
}
