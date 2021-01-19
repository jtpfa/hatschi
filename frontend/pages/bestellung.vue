<template>
    <client-only>
        <main class="container mt-5">
            <order-progressbar v-if="step >= 1 && step <= 3" class="position-relative mt-5" />

            <order-form />
        </main>
    </client-only>
</template>
<script>
/**
 * @component OrderPage
 * @desc Order process page with multi step form
 * @lifecycle mounted - Checks whether products are in cart and which step has to be filled in
 * @lifecyle watchQuery - Change the step accordingly to the url query
 * @author Jonas Pfannkuche
 */
import OrderForm from '~/components/shop/order/form'
import OrderProgressbar from '~/components/shop/order/progressbar'

export default {
    components: { OrderProgressbar, OrderForm },
    middleware: ['auth'],
    computed: {
        /**
         * @computed {Number} step - Current step of order process
         */
        step: {
            get() {
                return this.$store.state.order.step
            },
            set(step) {
                this.$store.commit('order/updateOrderInformation', { key: 'step', data: step })
            },
        },
        /**
         * @computed {Object} order - All order related information: {@link store:Order}
         */
        order() {
            return this.$store.state.order
        },
    },
    mounted() {
        if (this.$store.state.shoppingcart.cart.length === 0) {
            this.$router.push('/warenkorb')
            return
        }

        if (this.$route.query.step) {
            this.step = +this.$route.query.step

            if (
                (this.step === 2 || this.step === 3) &&
                (this.order.shippingMethod.id.length === 0 || this.order.shippingAddress.id === -1)
            ) {
                this.step = 1
                this.$router.push({ path: '/bestellung', query: { step: this.step } })
            }

            if (this.step === 3 && this.order.paymentMethod.id.length === 0) {
                this.step = 2
                this.$router.push({ path: '/bestellung', query: { step: this.step } })
            }

            this.$router.push({ path: '/bestellung', query: { step: this.$route.query.step } })
        } else {
            this.$router.push({ path: '/bestellung', query: { step: this.step } })
        }
    },
    head() {
        return {
            title: `PC Masterrace – ${this.$route.name.replace(/^\w/, c => c.toUpperCase())}`,
        }
    },
    watchQuery(query) {
        if (this) {
            this.step = +query.step
        }
    },
}
</script>

<style scoped></style>
