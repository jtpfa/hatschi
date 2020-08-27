<template>
    <div>
        <order-shipping-method class="mb-5" />

        <order-addresses v-if="shippingMethod !== null && shippingMethod.length > 0" class="mb-5">
            <b-alert :show="fetchErrorMsg.length > 0" variant="warning">{{ fetchErrorMsg }}</b-alert>
        </order-addresses>
    </div>
</template>

<script>
import OrderAddresses from '~/components/shop/order/steps/step1/addresses'
import OrderShippingMethod from '~/components/shop/order/steps/step1/shippingMethod'

export default {
    name: 'OrderStep1',
    components: { OrderAddresses, OrderShippingMethod },
    props: {
        fetchErrorMsg: {
            type: String,
            default: '',
        },
    },
    computed: {
        shippingMethod() {
            return this.$store.state.order.shippingMethod.name
        },
    },
}
</script>

<style scoped></style>
