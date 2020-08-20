<template>
    <client-only>
        <b-container class="mt-5">
            <order-progressbar v-if="step >= 1 && step <= 3" class="position-relative mt-5" />

            <order-form />
        </b-container>
    </client-only>
</template>

<script>
import OrderForm from '~/components/order/form'
import OrderProgressbar from '~/components/order/progressbar'

export default {
    name: 'Order',
    components: { OrderForm, OrderProgressbar },
    middleware: 'auth',
    computed: {
        step: {
            get() {
                return this.$store.state.order.step
            },
            set(step) {
                this.$store.commit('order/updateOrderInformation', { key: 'step', data: step })
            },
        },
    },
    mounted() {
        if (this.$store.state.shoppingcart.cart.length === 0) {
            this.$router.push('/warenkorb')
            return
        }

        if (this.$route.query.step) {
            this.step = +this.$route.query.step
            this.$router.push({ path: '/bestellung', query: { step: this.$route.query.step } })
        } else {
            this.$router.push({ path: '/bestellung', query: { step: this.step } })
        }
    },
    watchQuery(newQuery) {
        this.step = +newQuery.step
    },
}
</script>

<style scoped></style>
