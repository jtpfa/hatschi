<template>
    <div>
        <order-payment-method class="mb-5" />

        <order-vouchers v-if="paymentMethod !== null && paymentMethod.length > 0" class="mb-5" />
    </div>
</template>

<script>
import OrderPaymentMethod from '~/components/order/paymentMethod'
import OrderVouchers from '~/components/order/vouchers'

export default {
    name: 'OrderStep2',
    components: { OrderVouchers, OrderPaymentMethod },
    computed: {
        paymentMethod() {
            return this.$store.state.order.paymentMethod
        },
    },
}
</script>

<style scoped></style>
