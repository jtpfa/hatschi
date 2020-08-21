<template>
    <div>
        <order-payment-method class="mb-5" />

        <order-vouchers class="mb-5" />
    </div>
</template>

<script>
import OrderPaymentMethod from '~/components/shop/order/steps/step2/paymentMethod'
import OrderVouchers from '~/components/shop/order/steps/step2/vouchers'

export default {
    name: 'OrderStep2',
    components: { OrderVouchers, OrderPaymentMethod },
}
</script>

<style scoped></style>
