<template>
    <div>
        <order-adress-list address-type="shipping" />
        <slot />
        <b-button v-if="addresses.length === 0" to="/profil" variant="primary">Neue Adresse hinzufügen</b-button>

        <b-form-checkbox v-model="differentInvoiceAddress" class="my-5" name="differentInvoiceAddress" size="lg" switch>
            Rechnungsadresse weicht von der Lieferadresse ab
        </b-form-checkbox>

        <template v-if="differentInvoiceAddress">
            <order-adress-list address-type="invoice" />
            <slot />
            <b-button v-if="addresses.length === 0" to="/profil" variant="primary">Neue Adresse hinzufügen</b-button>
        </template>
    </div>
</template>

<script>
import OrderAdressList from '~/components/shop/order/steps/step1/adressList'

export default {
    name: 'OrderAddresses',
    components: { OrderAdressList },
    computed: {
        differentInvoiceAddress: {
            get() {
                return this.$store.state.order.differentInvoiceAddress
            },
            set(differentInvoiceAddress) {
                this.$store.commit('order/updateOrderInformation', {
                    key: 'differentInvoiceAddress',
                    data: differentInvoiceAddress,
                })
            },
        },
        addresses() {
            return this.$store.state.order.addresses
        },
    },
}
</script>

<style lang="scss" scoped></style>
