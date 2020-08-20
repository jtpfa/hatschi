<template>
    <client-only>
        <b-container class="mt-5">
            <order-progressbar v-if="step >= 1 && step <= 3" class="position-relative mt-5" />
            <b-form ref="form" novalidate @submit.prevent.stop="onSubmit">
                <order-headline />

                <div class="row mt-5">
                    <div class="col-md-7 mb-0">
                        <order-step1 v-if="step === 1" />

                        <order-step2 v-else-if="step === 2" />

                        <order-step3 v-else-if="step === 3" />

                        <template v-else-if="step < 1 || step > 4">
                            <b-jumbotron
                                bg-variant="light"
                                class="my-5"
                                header="Upps... Diesen Schritt gibt es nicht"
                                :header-level="4"
                            >
                                <p>
                                    Leider gab es einen Fehler im Bestellprozess. Die bereits eingegeben Daten gehen
                                    allerdings
                                    <strong>nicht</strong>
                                    verloren!
                                </p>
                                <b-button @click="goToInitialStep">
                                    Zum ersten Schritt
                                </b-button>
                            </b-jumbotron>
                        </template>
                    </div>
                    <div v-if="step >= 1 && step <= 3" class="col-md-5">
                        <cart-summary class="mb-5 mb-md-0" :error="error">
                            <b-button
                                v-if="step === 3"
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

                <order-pagination v-if="step >= 1 && step <= 3" :loading="loading" @back="stepBack" />
            </b-form>
        </b-container>
    </client-only>
</template>

<script>
import CartSummary from '~/components/cart/summary'
import OrderHeadline from '~/components/order/headline'
import OrderPagination from '~/components/order/pagination'
import OrderProgressbar from '~/components/order/progressbar'
import OrderStep1 from '~/components/order/steps/orderStep1'
import OrderStep2 from '~/components/order/steps/orderStep2'
import OrderStep3 from '~/components/order/steps/orderStep3'

export default {
    name: 'Order',
    components: {
        OrderPagination,
        OrderProgressbar,
        OrderStep3,
        OrderStep2,
        OrderStep1,
        OrderHeadline,
        CartSummary,
    },
    middleware: 'auth',
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
                if (this.step === 3) {
                    // await this.submitOrder()

                    if (this.error.length > 0) {
                        this.loading = false
                        return
                    }

                    this.step += 1
                    this.$router.push('/bestell-bestaetigung')
                } else {
                    this.step += 1
                    this.$router.push({ path: '/bestellung', query: { step: this.step } })
                }
            }
            this.loading = false
        },
        stepBack() {
            this.$refs.form.classList.remove('was-validated')
            this.step -= 1
            this.$router.push({ path: '/bestellung', query: { step: this.step } })
            this.$router.app.refresh()
        },
        goToInitialStep() {
            this.step = 1
            this.$router.push({ path: '/bestellung', query: { step: this.step } })
            this.$router.app.refresh()
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
