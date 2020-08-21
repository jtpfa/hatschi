<template>
    <div>
        <h2>Deine Daten</h2>
        <order-shipping-and-payment />

        <order-overview-address address-type="shipping" class="mb-5" title="Lieferadresse" />

        <order-overview-address
            v-if="order.differentInvoiceAddress"
            address-type="invoice"
            class="mb-5"
            title="Rechnungsadresse"
        />

        <order-overview-vouchers v-if="order.giftCardCode.length > 0 || order.discountCode.length > 0" class="mb-5" />

        <h2>Ausgewählte Artikel</h2>

        <cart-products :order="true" />
    </div>
</template>

<script>
import CartProducts from '~/components/shop/cart/products'
import OrderOverviewAddress from '~/components/shop/order/steps/step3/address'
import OrderShippingAndPayment from '~/components/shop/order/steps/step3/shippingAndPayment'
import OrderOverviewVouchers from '~/components/shop/order/steps/step3/vouchers'

export default {
    name: 'OrderStep3',
    components: { OrderOverviewVouchers, OrderShippingAndPayment, OrderOverviewAddress, CartProducts },
    computed: {
        order() {
            return this.$store.state.order
        },
    },
}
</script>

<style scoped></style>
