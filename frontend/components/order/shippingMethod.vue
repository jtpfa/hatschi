<template>
    <div>
        <h2>
            Versandart wählen
            <span class="mandatory">*</span>
        </h2>
        <b-list-group>
            <b-list-group-item v-for="method in availableMethods" :key="method.name + method.description">
                <b-form-radio
                    v-model="selectedMethod"
                    name="shipping-methods"
                    required
                    :state="validState"
                    :value="method.description + ' – ' + method.name"
                >
                    <span class="shipping-key">{{ method.description }} – {{ method.name }}</span>
                    <br />
                    <span class="text-muted">{{ method.shippingTime }}</span>
                </b-form-radio>
            </b-list-group-item>
        </b-list-group>
    </div>
</template>

<script>
export default {
    name: 'OrdershippingMethod',
    data() {
        return {
            validState: null,
            availableMethods: [
                { name: 'UPS', description: 'Standardversand', shippingTime: 'Lieferung innerhalb von 2-4 Werktagen' },
                { name: 'DHL', description: 'Expressversand', shippingTime: 'Lieferung innerhalb von 1-2 Werktagen' },
            ],
        }
    },
    computed: {
        selectedMethod: {
            get() {
                return this.$store.state.order.shippingMethod
            },
            set(shippingMethod) {
                this.$store.commit('order/updateOrderInformation', { key: 'shippingMethod', data: shippingMethod })
            },
        },
    },
}
</script>

<style lang="scss" scoped>
.shipping-key {
    font-size: 1.2rem;
}

span.mandatory {
    color: $danger;
}
</style>
