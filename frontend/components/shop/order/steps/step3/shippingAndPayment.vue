<template>
    <b-card-group class="mb-5" deck>
        <order-overview-card :step-to-edit-data="1" title="Versandart">
            {{ order.shippingMethod.name }}
            {{ order.shippingMethod.description ? ` – ${order.shippingMethod.description}` : null }}
        </order-overview-card>
        <order-overview-card :step-to-edit-data="2" title="Zahlungsart">
            {{ order.paymentMethod.name }}
            {{ order.paymentMethod.description ? ` – ${order.paymentMethod.description}` : null }}
        </order-overview-card>
    </b-card-group>
</template>
<script>
/**
 * @component OrderShippingAndPayment
 * @desc Overview of the shipping and payment methods
 * @author Jonas Pfannkuche
 */

import OrderOverviewCard from '~/components/shop/order/steps/step3/card'

export default {
    name: 'OrderShippingAndPayment',
    components: { OrderOverviewCard },
    computed: {
        /**
         * @computed {Object} order - All order related information: {@link store:Order}
         */
        order() {
            return this.$store.state.order
        },
    },
}
</script>

<style scoped></style>
