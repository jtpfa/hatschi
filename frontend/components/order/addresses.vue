<template>
    <div>
        <order-address-form address-type="shipping" />

        <b-form-checkbox v-model="differentInvoiceAddress" class="my-5" name="differentInvoiceAddress" size="lg" switch>
            Rechnungsadresse weicht von der Lieferadresse ab
        </b-form-checkbox>

        <order-address-form v-if="differentInvoiceAddress" address-type="invoice" />
    </div>
</template>

<script>
import OrderAddressForm from '~/components/order/addressForm'

export default {
    name: 'OrderAddresses',
    components: { OrderAddressForm },
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
    },
}
</script>

<style lang="scss" scoped></style>
