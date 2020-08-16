<template>
    <client-only>
        <b-container class="mt-5">
            <order-headline />
            <b-form ref="form" novalidate @submit.prevent.stop="onSubmit">
                <div class="row my-5">
                    <div class="col-md-7 mb-0">
                        <order-step1 v-if="step === 0" />

                        <order-step2 v-else-if="step === 1" />

                        <order-step3 v-else-if="step === 2" />

                        <template v-else>
                            <p>Fehler</p>
                        </template>
                    </div>
                    <div class="col-md-5">
                        <cart-summary :error="error">
                            <b-button class="my-3 w-100" :disabled="loading" size="lg" type="submit" variant="primary">
                                {{ step === 2 ? 'Jetzt kaufen' : 'Weiter' }}
                            </b-button>
                            <b-button v-if="step === 0" class="w-100" size="lg" to="/warenkorb" variant="light">
                                Zurück
                            </b-button>
                            <b-button v-else-if="step > 0" class="w-100" size="lg" variant="light" @click="stepBack">
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
import OrderStep1 from '~/components/order/steps/orderStep1'
import OrderStep2 from '~/components/order/steps/orderStep2'
import OrderStep3 from '~/components/order/steps/orderStep3'

export default {
    name: 'Order',
    components: {
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
                    this.submitOrder()
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
        submitOrder() {
            console.log('orderSubmitted')
        },
    },
}
</script>

<style scoped></style>
