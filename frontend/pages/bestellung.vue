<template>
    <client-only>
        <b-container class="mt-5">
            <order-progressbar class="position-relative mt-5" />
            <b-form ref="form" novalidate @submit.prevent.stop="onSubmit">
                <order-headline />

                <div class="row mt-5">
                    <div class="col-md-7 mb-0">
                        <order-step1 v-if="step === 0" />

                        <order-step2 v-else-if="step === 1" />

                        <order-step3 v-else-if="step === 2" />

                        <order-confirmation v-else-if="step === 3" />

                        <template v-else>
                            <b-jumbotron
                                bg-variant="light"
                                class="my-5"
                                header="Upps... Das hätte nicht passieren dürfen."
                                :header-level="4"
                            >
                                <p>
                                    Leider gab es einen Fehler im Bestellprozess. Die bereits eingegeben Daten gehen
                                    allerdings
                                    <strong>nicht</strong>
                                    verloren!
                                </p>
                                <b-button @click="step = 0">Erneut versuchen</b-button>
                            </b-jumbotron>
                        </template>
                    </div>
                    <div v-if="step >= 0 && step <= 2" class="col-md-5">
                        <cart-summary class="mb-5 mb-md-0" :error="error">
                            <b-button
                                v-if="step === 2"
                                class="d-flex justify-content-center w-100 align-items-center my-3"
                                :disabled="loading"
                                size="lg"
                                type="submit"
                                variant="primary"
                            >
                                <b-spinner v-if="loading" small></b-spinner>
                                Jetzt kaufen
                            </b-button>
                        </cart-summary>
                    </div>
                </div>

                <order-pagination :loading="loading" @back="stepBack" />
            </b-form>
        </b-container>
    </client-only>
</template>

<script>
import CartSummary from '~/components/cart/summary'
import OrderHeadline from '~/components/order/headline'
import OrderPagination from '~/components/order/pagination'
import OrderProgressbar from '~/components/order/progressbar'
import OrderConfirmation from '~/components/order/steps/orderConfirmation'
import OrderStep1 from '~/components/order/steps/orderStep1'
import OrderStep2 from '~/components/order/steps/orderStep2'
import OrderStep3 from '~/components/order/steps/orderStep3'

export default {
    name: 'Order',
    components: {
        OrderPagination,
        OrderConfirmation,
        OrderProgressbar,
        OrderStep3,
        OrderStep2,
        OrderStep1,
        OrderHeadline,
        CartSummary,
    },
    data() {
        return {
            loading: false,
            error: '',
        }
    },
    computed: {
        step: {
            get() {
                return this.$store.state.order.step
            },
            set(step) {
                this.$store.commit('order/updateOrderInformation', { key: 'step', data: step })
            },
        },
        cart() {
            return this.$store.state.shoppingcart.cart
        },
        order() {
            return this.$store.state.order
        },
        shippingMethod() {
            return this.$store.state.order.shippingMethod
        },
        paymentMethod() {
            return this.$store.state.order.paymentMethod
        },
    },
    methods: {
        async onSubmit(event) {
            this.$refs.form.classList.remove('was-validated')
            this.loading = true
            this.error = ''
            if (!this.$refs.form.checkValidity()) {
                this.$refs.form.classList.add('was-validated')
                event.preventDefault()
                event.stopPropagation()
            } else {
                // do not show validation state if everything is fine
                // because the next step would be validated as well

                // eslint-disable-next-line no-lonely-if
                if (this.step === 2) {
                    if (!this.$auth.loggedIn) {
                        this.$auth.$storage.setUniversal('redirect', '/bestellung')
                        this.$router.push('/auth/login')
                    } else {
                        this.$auth.$storage.removeUniversal('redirect')
                        await this.submitOrder()

                        if (this.error.length > 0) {
                            this.loading = false
                            return
                        }

                        this.step += 1
                    }
                } else {
                    this.step += 1
                }
            }
            this.loading = false
        },
        stepBack() {
            this.$refs.form.classList.remove('was-validated')
            this.step -= 1
        },
        async submitOrder() {
            try {
                const shippingAddress = this.getAddress('shipping')
                const invoiceAddress = this.order.differentInvoiceAddress ? this.getAddress('invoice') : shippingAddress
                const orderItems = this.cart.map(item => {
                    return { article: item.id, quantity: item.quantity }
                })

                await this.$api.placeOrder(
                    { orderItems, shippingAddress, invoiceAddress },
                    this.$auth.getToken('keycloak')
                )
            } catch (err) {
                this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
            }
        },
        getAddress(addressType) {
            return {
                firstName: this.order[`${addressType}Address`].firstName,
                lastName: this.order[`${addressType}Address`].lastName,
                address: `${this.order[`${addressType}Address`].street} ${this.order[`${addressType}Address`].number}`,
                additionalAddress: this.order[`${addressType}Address`].additionalAddress,
                zip: this.order[`${addressType}Address`].zip,
                city: this.order[`${addressType}Address`].city,
                country: this.order[`${addressType}Address`].country,
            }
        },
    },
}
</script>

<style scoped></style>
