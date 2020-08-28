<template>
    <div>
        <order-adress-list address-type="shipping" />
        <slot />
        <b-button class="my-4" to="/profil" variant="primary">
            {{ addresses.length === 0 ? 'Neue Adresse hinzufügen' : 'Adressen verwalten' }}
        </b-button>

        <b-form-checkbox v-model="differentInvoiceAddress" class="my-5" name="differentInvoiceAddress" size="lg" switch>
            Rechnungsadresse weicht von der Lieferadresse ab
        </b-form-checkbox>

        <template v-if="differentInvoiceAddress">
            <order-adress-list address-type="invoice" />
            <slot />
            <b-button class="my-4" to="/profil" variant="primary">
                {{ addresses.length === 0 ? 'Neue Adresse hinzufügen' : 'Adressen verwalten' }}
            </b-button>
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
