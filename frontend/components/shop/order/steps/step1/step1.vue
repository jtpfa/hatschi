<template>
    <div>
        <order-shipping-method class="mb-5" />

        <order-addresses
            v-if="shippingMethod !== null && shippingMethod.length > 0"
            class="mb-5"
            :fetch-error-msg="fetchErrorMsg"
        >
            <b-alert :show="fetchErrorMsg.length > 0" variant="warning">{{ fetchErrorMsg }}</b-alert>
            <b-button class="my-4" to="/profil" variant="primary">
                {{ addresses.length === 0 ? 'Neue Adresse hinzufügen' : 'Adressen verwalten' }}
            </b-button>
        </order-addresses>
    </div>
</template>

<script>
import OrderAddresses from '~/components/shop/order/steps/step1/addresses'
import OrderShippingMethod from '~/components/shop/order/steps/step1/shippingMethod'

export default {
    name: 'OrderStep1',
    components: { OrderAddresses, OrderShippingMethod },
    props: {
        fetchErrorMsg: {
            type: String,
            default: '',
        },
    },
    computed: {
        addresses() {
            return this.$store.state.order.addresses
        },
        shippingMethod() {
            return this.$store.state.order.shippingMethod.name
        },
    },
}
</script>

<style scoped></style>
