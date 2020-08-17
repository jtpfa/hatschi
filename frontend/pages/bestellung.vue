<template>
    <client-only>
        <b-container class="mt-5">
            <order-progressbar class="position-relative mt-5" />
            <b-form ref="form" novalidate @submit.prevent.stop="onSubmit">
                <order-pagination @back="stepBack" />
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
                        <cart-summary class="mb-5 mb-md-0" :error="error" />
                    </div>
                </div>

                <order-pagination @back="stepBack" />
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
        deliveryMethod() {
            return this.$store.state.order.deliveryMethod
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
                    // this.submitOrder()

                    await new Promise(res => setTimeout(res, 3000))
                }
                this.step += 1
            }
            this.loading = false
        },
        stepBack() {
            this.$refs.form.classList.remove('was-validated')
            this.step -= 1
        },
        submitOrder() {},
    },
}
</script>

<style scoped></style>
