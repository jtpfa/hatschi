package de.pcmr.shop.api.model;

import de.pcmr.shop.domain.OrderStatusEnum;
import de.pcmr.shop.domain.PaymentMethodEnum;
import de.pcmr.shop.domain.ShippingMethodEnum;

import java.util.Date;
import java.util.List;

/**
 * Data transfer object to output order information.
 *
 * @author Fynn Lohse
 */

public class OrderDTO {
    private Long id;
    private OrderStatusEnum orderStatus;
    private Date orderDate;
    private String customerEmail;
    private List<OrderItemDetailsDTO> orderItems;
    private Boolean paid;
    private AddressCreationDTO invoiceAddress;
    private AddressCreationDTO shippingAddress;
    private PaymentMethodEnum paymentMethod;
    private ShippingMethodEnum shippingMethod;

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<OrderItemDetailsDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDetailsDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public AddressCreationDTO getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(AddressCreationDTO invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public AddressCreationDTO getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressCreationDTO shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ShippingMethodEnum getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethodEnum shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
