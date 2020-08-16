<template>
    <client-only>
        <b-container class="mt-5">
            <order-headline />
            <b-form ref="form" novalidate @submit.prevent="onSubmit">
                <div class="row my-5">
                    <div class="col-md-7 mb-0">
                        <template v-if="step === 0">
                            <order-delivery-method class="mb-5" />

                            <order-addresses v-if="deliveryMethod !== null && deliveryMethod.length > 0" class="mb-5" />
                        </template>
                        <template v-else-if="step === 1">
                            <order-payment-method class="mb-5" />

                            <order-vouchers v-if="paymentMethod !== null && paymentMethod.length > 0" class="mb-5" />
                        </template>
                    </div>
                    <div class="col-md-5">
                        <cart-summary :error="error">
                            <b-button class="my-3 w-100" :disabled="loading" size="lg" type="submit" variant="primary">
                                Weiter
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
import OrderAddresses from '~/components/order/addresses'
import OrderDeliveryMethod from '~/components/order/deliveryMethod'
import OrderHeadline from '~/components/order/headline'
import OrderPaymentMethod from '~/components/order/paymentMethod'
import OrderVouchers from '~/components/order/vouchers'

export default {
    name: 'Order',
    components: { OrderVouchers, OrderPaymentMethod, OrderHeadline, OrderAddresses, OrderDeliveryMethod, CartSummary },
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
                // do not show validation state if everything is fine because the next would be validated as well
                this.step += 1
            }
            this.loading = false
        },
        stepBack() {
            this.$refs.form.classList.remove('was-validated')
            this.step -= 1
        },
    },
}
</script>

<style scoped></style>
