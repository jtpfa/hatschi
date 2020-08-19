<template>
    <div>
        <h2>
            Zahlungsart
            <span class="mandatory">*</span>
        </h2>
        <b-list-group>
            <b-list-group-item v-for="method in availableMethods" :key="method.name">
                <b-form-radio
                    v-model="selectedMethod"
                    name="shipping-methods"
                    required
                    :state="validState"
                    :value="method.description ? `${method.name} – ${method.description}` : method.name"
                >
                    <span class="shipping-key">
                        {{ method.name }}{{ method.description ? ` – ${method.description}` : null }}
                    </span>
                </b-form-radio>
            </b-list-group-item>
        </b-list-group>
    </div>
</template>

<script>
export default {
    name: 'OrderPaymentMethod',
    data() {
        return {
            validState: null,
            availableMethods: [
                { name: 'Klarna', description: 'Kauf auf Rechnung' },
                { name: 'Paypal', description: '' },
                { name: 'Kreditkarte', description: '' },
            ],
        }
    },
    computed: {
        selectedMethod: {
            get() {
                return this.$store.state.order.paymentMethod
            },
            set(paymentMethod) {
                this.$store.commit('order/updateOrderInformation', { key: 'paymentMethod', data: paymentMethod })
            },
        },
    },
}
</script>

<style lang="scss" scoped>
span.mandatory {
    color: $danger;
}
</style>
