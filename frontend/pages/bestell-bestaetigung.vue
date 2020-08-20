<template>
    <b-container class="my-5">
        <order-progressbar class="position-relative mt-5" :ready="true" />

        <order-confirmation />
    </b-container>
</template>

<script>
import OrderProgressbar from '~/components/order/progressbar'
import OrderConfirmation from '~/components/order/steps/orderConfirmation'

export default {
    name: 'BestellBestaetigung',
    components: { OrderConfirmation, OrderProgressbar },
    mounted() {
        this.$store.commit('shoppingcart/clearCart')
        this.$store.commit('order/updateOrderInformation', { key: 'step', data: 1 })
        this.$router.app.refresh()
    },
    head() {
        return {
            title: `${this.$route.name.replace(/^\w/, c => c.toUpperCase())} – PC Masterrace`,
        }
    },
}
</script>

<style scoped></style>
