package de.pcmr.shop.domain;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static de.pcmr.shop.domain.AbstractEntity.TABLE_PREFIX;

@Entity
@Table(name = TABLE_PREFIX + "orders")
public class OrderEntity extends AbstractEntity {

    @Column(nullable = false)
    private OrderStatusEnum orderStatus;

    @Column(nullable = false)
    @PastOrPresent
    private Date orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CustomerEntity customer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    @Size(min = 1, max = 999)
    private List<OrderItemEntity> orderItems;

    @Column(nullable = false)
    private Boolean paid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    private AddressEntity invoiceAddress;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(nullable = false)
    private AddressEntity shippingAddress;

    @Column(nullable = false)
    private PaymentMethodEnum paymentMethod;

    @Column(nullable = false)
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

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public AddressEntity getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(AddressEntity invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public AddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressEntity shippingAddress) {
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
}
