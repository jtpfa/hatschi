<template>
    <div>
        <h2>Deine Daten</h2>
        <b-card-group class="mb-5" deck>
            <order-overview-card :step-to-edit-data="0" title="Versandart">
                {{ order.deliveryMethod }}
            </order-overview-card>
            <order-overview-card :step-to-edit-data="1" title="Zahlungsart">
                {{ order.paymentMethod }}
            </order-overview-card>
        </b-card-group>

        <order-overview-address address-type="delivery" class="mb-5" title="Lieferadresse" />

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
import CartProducts from '~/components/cart/products'
import OrderOverviewAddress from '~/components/order/overview/address'
import OrderOverviewCard from '~/components/order/overview/card'
import OrderOverviewVouchers from '~/components/order/overview/vouchers'

export default {
    name: 'OrderOverview',
    components: { OrderOverviewCard, CartProducts, OrderOverviewVouchers, OrderOverviewAddress },
    computed: {
        order() {
            return this.$store.state.order
        },
    },
}
</script>

<style lang="scss" scoped></style>
