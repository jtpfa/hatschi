<template>
    <div>
        <order-payment-method class="mb-5" />

        <order-vouchers class="mb-5" />
    </div>
</template>

<script>
import OrderPaymentMethod from '~/components/order/paymentMethod'
import OrderVouchers from '~/components/order/vouchers'

export default {
    name: 'OrderStep2',
    components: { OrderVouchers, OrderPaymentMethod },
}
</script>

<style scoped></style>
