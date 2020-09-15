package de.pcmr.shop.api.model;

import de.pcmr.shop.domain.PaymentMethodEnum;
import de.pcmr.shop.domain.ShippingMethodEnum;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Fynn Lohse
 */

public class OrderCreationDTO {
    @NotEmpty
    private List<@Valid OrderItemDTO> orderItems;
    @NotNull
    private Long invoiceAddressId;
    @NotNull
    private Long shippingAddressId;
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

    public Long getInvoiceAddressId() {
        return invoiceAddressId;
    }

    public void setInvoiceAddressId(Long invoiceAddressId) {
        this.invoiceAddressId = invoiceAddressId;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
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
