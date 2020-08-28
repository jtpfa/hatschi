<template>
    <div>
        <h2>
            {{ addressType === 'shipping' ? 'Lieferadresse' : 'Rechnungsadresse' }} auswählen
            <span class="mandatory">*</span>
        </h2>
        <b-list-group>
            <b-list-group-item v-for="(item, index) in addresses" :key="item.id">
                <b-form-radio
                    v-model="selectedAddress"
                    :name="`${addressType}-address`"
                    :required="addressType === 'shipping' || differentInvoiceAddress"
                    :state="validState"
                    :value="{ id: item.id, index }"
                >
                    <span>{{ item.firstName }} {{ item.lastName }}</span>
                    <br />
                    <span class="text-muted">
                        {{ item.address }}, {{ item.zip }} {{ item.city }},
                        {{ item.additionalAddress.length > 0 ? `${item.additionalAddress},` : null }} {{ item.country }}
                    </span>
                </b-form-radio>
                <b-form-radio class="d-none" :name="`${addressType}-address`" />
            </b-list-group-item>
        </b-list-group>
    </div>
</template>

<script>
export default {
    name: 'OrderAdressList',
    props: {
        addressType: {
            type: String,
            required: true,
            validator(type) {
                // The value must match one of these strings
                return ['shipping', 'invoice'].indexOf(type) !== -1
            },
        },
    },
    data() {
        return {
            validState: null,
        }
    },
    computed: {
        selectedAddress: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`]
            },
            set(address) {
                this.$store.commit('order/updateOrderInformation', {
                    key: `${this.addressType}Address`,
                    data: address,
                })
            },
        },
        addresses() {
            return this.$store.state.order.addresses
        },
        differentInvoiceAddress() {
            return this.$store.state.order.differentInvoiceAddress
        },
    },
}
</script>

<style lang="scss" scoped>
span.mandatory {
    color: $danger;
}
</style>
