package de.pcmr.shop.api.model;

import de.pcmr.shop.domain.PaymentMethodEnum;
import de.pcmr.shop.domain.ShippingMethodEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderCreationDTO {
    @NotEmpty
    private List<OrderItemDTO> orderItems;
    @NotNull
    private AddressDTO invoiceAddress;
    @NotNull
    private AddressDTO shippingAddress;
    @NotNull
    private PaymentMethodEnum paymentMethod;
    @NotNull
    private ShippingMethodEnum shippingMethod;

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
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
}
