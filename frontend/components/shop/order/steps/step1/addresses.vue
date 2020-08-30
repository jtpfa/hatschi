<template>
    <div>
        <order-adress-list v-if="fetchErrorMsg.length === 0" address-type="shipping" />
        <slot />

        <b-form-checkbox v-model="differentInvoiceAddress" class="my-5" name="differentInvoiceAddress" size="lg" switch>
            Rechnungsadresse weicht von der Lieferadresse ab
        </b-form-checkbox>

        <template v-if="differentInvoiceAddress">
            <order-adress-list v-if="fetchErrorMsg.length === 0" address-type="invoice" />
            <slot />
        </template>
    </div>
</template>

<script>
import OrderAdressList from '~/components/shop/order/steps/step1/adressList'

export default {
    name: 'OrderAddresses',
    components: { OrderAdressList },
    props: {
        fetchErrorMsg: {
            type: String,
            default: '',
        },
    },
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
