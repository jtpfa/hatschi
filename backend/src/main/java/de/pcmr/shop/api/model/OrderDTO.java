package de.pcmr.shop.api.model;

import de.pcmr.shop.domain.OrderStatusEnum;
import de.pcmr.shop.domain.PaymentMethodEnum;
import de.pcmr.shop.domain.ShippingMethodEnum;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Long id;
    private OrderStatusEnum orderStatus;
    private Date orderDate;
    private String customerEmail;
    private List<OrderItemDetailsDTO> orderItems;
    private Boolean paid;
    private AddressDTO invoiceAddress;
    private AddressDTO shippingAddress;
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

    public AddressDTO getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(AddressDTO invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public AddressDTO getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDTO shippingAddress) {
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
