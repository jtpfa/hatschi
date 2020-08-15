<template>
    <div>
        <order-address-form address-type="delivery" />

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
    data() {
        return {
            differentInvoiceAddress: false,
        }
    },
}
</script>

<style lang="scss" scoped></style>
