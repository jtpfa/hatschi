<template>
    <client-only>
        <b-container class="mt-5">
            <order-progressbar class="position-relative mt-5" />
            <order-headline />
            <b-form ref="form" novalidate @submit.prevent.stop="onSubmit">
                <div class="row my-5">
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
                        <cart-summary :error="error">
                            <b-button
                                class="d-flex justify-content-center align-items-center my-3 w-100"
                                :disabled="loading"
                                size="lg"
                                type="submit"
                                variant="primary"
                            >
                                <b-spinner v-if="loading" class="mr-3" small></b-spinner>
                                {{ step === 2 ? 'Jetzt kaufen' : 'Weiter' }}
                            </b-button>
                            <b-button v-if="step === 0" class="w-100" size="lg" to="/warenkorb" variant="light">
                                Zurück
                            </b-button>
                            <b-button
                                v-else-if="step > 0"
                                class="w-100"
                                :disabled="loading"
                                size="lg"
                                variant="light"
                                @click="stepBack"
                            >
                                Zurück
                            </b-button>
                        </cart-summary>
                    </div>
                </div>
            </b-form>
        </b-container>
    </client-only>
</template>

<script>
import CartSummary from '~/components/cart/summary'
import OrderHeadline from '~/components/order/headline'
import OrderProgressbar from '~/components/order/progressbar'
import OrderConfirmation from '~/components/order/steps/orderConfirmation'
import OrderStep1 from '~/components/order/steps/orderStep1'
import OrderStep2 from '~/components/order/steps/orderStep2'
import OrderStep3 from '~/components/order/steps/orderStep3'

export default {
    name: 'Order',
    components: {
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
        onSubmit(event) {
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

                    setTimeout(() => {
                        this.step += 1
                        this.loading = false
                    }, 3000)
                } else {
                    this.step += 1
                    this.loading = false
                }
            }
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
