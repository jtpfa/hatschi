<template>
    <div>
        <order-shipping-method class="mb-5" />

        <order-addresses
            v-if="shippingMethod !== null && shippingMethod.length > 0"
            class="mb-5"
            :fetch-error-msg="fetchErrorMsg"
        />
    </div>
</template>

<script>
/**
 * @component OrderStep1
 * @desc First step of the order process (shipping method and addresses)
 * @author Jonas Pfannkuche
 */

import OrderAddresses from '~/components/shop/order/steps/step1/addresses'
import OrderShippingMethod from '~/components/shop/order/steps/step1/shippingMethod'

export default {
    name: 'OrderStep1',
    components: { OrderAddresses, OrderShippingMethod },
    props: {
        /**
         * @vprop {String} fetchErrorMsg - Error message of rejected fetch
         */
        fetchErrorMsg: {
            type: String,
            default: '',
        },
    },
    computed: {
        /**
         * @computed shippingMethod - Name of selected shipping method
         */
        shippingMethod() {
            return this.$store.state.order.shippingMethod.name
        },
    },
}
</script>

<style scoped></style>
