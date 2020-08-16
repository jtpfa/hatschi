<template>
    <div>
        <order-delivery-method class="mb-5" />

        <order-addresses v-if="deliveryMethod !== null && deliveryMethod.length > 0" class="mb-5" />
    </div>
</template>

<script>
import OrderAddresses from '~/components/order/addresses'
import OrderDeliveryMethod from '~/components/order/deliveryMethod'

export default {
    name: 'OrderStep1',
    components: { OrderAddresses, OrderDeliveryMethod },
    computed: {
        deliveryMethod() {
            return this.$store.state.order.deliveryMethod
        },
    },
}
</script>

<style scoped></style>
