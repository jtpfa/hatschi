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
                    name="delivery-methods"
                    required
                    :state="validState"
                    :value="method.description + ' – ' + method.name"
                >
                    <span class="delivery-key">{{ method.description }} – {{ method.name }}</span>
                    <br />
                    <span class="text-muted">{{ method.deliveryTime }}</span>
                </b-form-radio>
            </b-list-group-item>
        </b-list-group>
    </div>
</template>

<script>
export default {
    name: 'OrderDeliveryMethod',
    data() {
        return {
            validState: null,
            availableMethods: [
                { name: 'UPS', description: 'Standardversand', deliveryTime: 'Lieferung innerhalb von 2-4 Werktagen' },
                { name: 'DHL', description: 'Expressversand', deliveryTime: 'Lieferung innerhalb von 1-2 Werktagen' },
            ],
        }
    },
    computed: {
        selectedMethod: {
            get() {
                return this.$store.state.order.deliveryMethod
            },
            set(deliveryMethod) {
                this.$store.commit('order/updateOrderInformation', { key: 'deliveryMethod', data: deliveryMethod })
            },
        },
    },
}
</script>

<style lang="scss" scoped>
.delivery-key {
    font-size: 1.2rem;
}

span.mandatory {
    color: $danger;
}
</style>
