<template>
    <div>
        <order-shipping-method class="mb-5" />

        <order-addresses v-if="shippingMethod !== null && shippingMethod.length > 0" class="mb-5" />
    </div>
</template>

<script>
import OrderAddresses from '~/components/order/steps/step1/addresses'
import OrderShippingMethod from '~/components/order/steps/step1/shippingMethod'

export default {
    name: 'OrderStep1',
    components: { OrderAddresses, OrderShippingMethod },
    computed: {
        shippingMethod() {
            return this.$store.state.order.shippingMethod.name
        },
    },
}
</script>

<style scoped></style>
