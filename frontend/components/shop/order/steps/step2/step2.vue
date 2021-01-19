<template>
    <div>
        <order-payment-method class="mb-5" />

        <order-vouchers class="mb-5" />
    </div>
</template>

<script>
/**
 * @component OrderStep2
 * @desc Second step of the order process (payment method and vouchers)
 * @author Jonas Pfannkuche
 */

import OrderPaymentMethod from '~/components/shop/order/steps/step2/paymentMethod'
import OrderVouchers from '~/components/shop/order/steps/step2/vouchers'

export default {
    name: 'OrderStep2',
    components: { OrderVouchers, OrderPaymentMethod },
}
</script>

<style scoped></style>
