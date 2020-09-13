<template>
    <div>
        <order-adress-list address-type="shipping" :fetch-error-msg="fetchErrorMsg" />

        <b-form-checkbox v-model="differentInvoiceAddress" class="my-5" name="differentInvoiceAddress" size="lg" switch>
            Rechnungsadresse weicht von der Lieferadresse ab
        </b-form-checkbox>

        <template v-if="differentInvoiceAddress">
            <order-adress-list address-type="invoice" :fetch-error-msg="fetchErrorMsg" />
        </template>
    </div>
</template>

<script>
/**
 * @component OrderAddresses
 * @desc Default: shows shipping addresses. If the user enables the "different invoice address" checkbox: shows shipping and invoice addresses
 * @author Jonas Pfannkuche
 */

import OrderAdressList from '~/components/shop/order/steps/step1/adressList'

export default {
    name: 'OrderAddresses',
    components: { OrderAdressList },
    props: {
        /**
         * @vprop {String} fetchErrorMsg - Error message of rejected fetch
         */
        fetchErrorMsg: {
            type: String,
            default: '',
        },
    },
    computed: {
        /**
         * @computed {Boolean} differentInvoiceAddress - Checkbox status (true = checked)
         */
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
