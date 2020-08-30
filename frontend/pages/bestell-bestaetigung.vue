<template>
    <b-container v-if="step !== 4" class="my-5">
        <order-progressbar class="position-relative mt-5" :ready="true" />

        <order-confirmation />
    </b-container>
</template>

<script>
import OrderProgressbar from '~/components/shop/order/progressbar'
import OrderConfirmation from '~/components/shop/order/steps/orderConfirmation'

export default {
    name: 'BestellBestaetigung',
    components: { OrderConfirmation, OrderProgressbar },
    computed: {
        step() {
            return this.$store.state.order.step
        },
    },
    mounted() {
        if (this.step !== 3) {
            this.$router.push({ path: '/bestellung', query: { step: 1 } })
        } else {
            this.$store.commit('shoppingcart/clearCart')
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
