<template>
    <div>
        <h2>
            Zahlungsart
            <span class="mandatory">*</span>
        </h2>
        <b-list-group>
            <b-list-group-item v-for="method in availableMethods" :key="method.id">
                <b-form-radio
                    v-model="selectedMethod"
                    name="shipping-methods"
                    required
                    :state="validState"
                    :value="{ id: method.id, name: method.name, description: method.description }"
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
/**
 * @component OrderPaymentMethod
 * @desc Radio group of all payment methods
 * @author Jonas Pfannkuche
 */

export default {
    name: 'OrderPaymentMethod',
    data() {
        return {
            /**
             * @member {Boolean|Function} [validState='null'] - Set to null to prevent validation when user navigates between steps
             */
            validState: null,
            /**
             * @member {Array.<{id: String, name: String, description: String}>} availableMethods - Available payment methods
             */
            availableMethods: [
                { id: 'INVOICE', name: 'Klarna', description: 'Kauf auf Rechnung' },
                { id: 'PAYPAL', name: 'Paypal', description: '' },
                { id: 'CREDIT_CARD', name: 'Kreditkarte', description: '' },
            ],
        }
    },
    computed: {
        /**
         * @computed {Object} selectedMethod - Selected payment method
         */
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
