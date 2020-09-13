<template>
    <main v-if="step !== 4" class="container my-5">
        <order-progressbar class="position-relative mt-5" :done="true" />

        <order-confirmation />
    </main>
</template>

<script>
/**
 * @component OrderConfirmationPage
 * @desc Order confirmation page
 * @lifecycle mounted - Checks if user has performed an order. In this case clear the cart and reset some values. Otherwise redirect to first step.
 * @author Jonas Pfannkuche
 */

import OrderProgressbar from '~/components/shop/order/progressbar'
import OrderConfirmation from '~/components/shop/order/steps/orderConfirmation'

export default {
    components: { OrderConfirmation, OrderProgressbar },
    computed: {
        /**
         * @computed {Number} step - Current step of order process
         */
        step() {
            return this.$store.state.order.step
        },
    },
    mounted() {
        if (this.step !== 3) {
            this.$router.push({ path: '/bestellung', query: { step: 1 } })
        } else {
            this.$store.commit('shoppingcart/clearCart')
            this.$store.commit('order/updateOrderInformation', {
                key: 'shippingAddress',
                data: { id: -1, index: -1 },
            })
            this.$store.commit('order/updateOrderInformation', {
                key: 'invoiceAddress',
                data: { id: -1, index: -1 },
            })
        }

        this.$store.commit('order/updateOrderInformation', { key: 'step', data: 1 })
    },
    head() {
        return {
            title: `${this.$route.name.replace(/^\w/, c => c.toUpperCase())} – PC Masterrace`,
        }
    },
}
</script>

<style scoped></style>
