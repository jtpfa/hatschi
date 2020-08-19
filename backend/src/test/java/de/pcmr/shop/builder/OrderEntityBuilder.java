package de.pcmr.shop.builder;

import de.pcmr.shop.domain.*;

import java.util.Date;
import java.util.List;

public final class OrderEntityBuilder {
    private OrderStatusEnum orderStatus;
    private Date orderDate;
    private CustomerEntity customer;
    private List<OrderItemEntity> orderItems;
    private Boolean paid;
    private AddressEntity invoiceAddress;
    private AddressEntity shippingAddress;
    private PaymentMethodEnum paymentMethod;
    private ShippingMethodEnum shippingMethod;
    private Long id;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;

    private OrderEntityBuilder() {
    }

    public static OrderEntityBuilder anOrderEntity() {
        return new OrderEntityBuilder();
    }

    public OrderEntityBuilder withOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderEntityBuilder withOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderEntityBuilder withCustomer(CustomerEntity customer) {
        this.customer = customer;
        return this;
    }

    public OrderEntityBuilder withOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public OrderEntityBuilder withPaid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public OrderEntityBuilder withInvoiceAddress(AddressEntity invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
        return this;
    }

    public OrderEntityBuilder withShippingAddress(AddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public OrderEntityBuilder withPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderEntityBuilder withShippingMethod(ShippingMethodEnum shippingMethod) {
        this.shippingMethod = shippingMethod;
        return this;
    }

    public OrderEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public OrderEntityBuilder withCreated(Date created) {
        this.created = created;
        return this;
    }

    public OrderEntityBuilder withUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    public OrderEntityBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OrderEntityBuilder withLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public OrderEntity build() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(orderStatus);
        orderEntity.setOrderDate(orderDate);
        orderEntity.setCustomer(customer);
        orderEntity.setOrderItems(orderItems);
        orderEntity.setPaid(paid);
        orderEntity.setInvoiceAddress(invoiceAddress);
        orderEntity.setShippingAddress(shippingAddress);
        orderEntity.setPaymentMethod(paymentMethod);
        orderEntity.setShippingMethod(shippingMethod);
        orderEntity.setId(id);
        orderEntity.setCreated(created);
        orderEntity.setUpdated(updated);
        orderEntity.setCreatedBy(createdBy);
        orderEntity.setLastModifiedBy(lastModifiedBy);
        return orderEntity;
    }
}
