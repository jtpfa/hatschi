<template>
    <div>
        <h2>
            {{ addressType === 'shipping' ? 'Lieferadresse' : 'Rechnungsadresse' }} auswählen
            <span v-if="addressType === 'shipping'" class="mandatory">*</span>
        </h2>
        <b-list-group>
            <b-list-group-item v-for="item in addresses" :key="item.id">
                <b-form-radio
                    v-model="selectedAddress"
                    :name="`${addressType}-address`"
                    :required="addressType === 'shipping'"
                    :state="validState"
                    :value="item.id"
                >
                    <span>{{ item.firstName }} {{ item.lastName }}</span>
                    <br />
                    <span>
                        {{ item.address }}, {{ item.zip }} {{ item.city }},
                        {{ item.additionalAddress.length > 0 ? `${item.additionalAddress},` : null }} {{ item.country }}
                    </span>
                </b-form-radio>
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
    },
}
</script>

<style lang="scss" scoped>
span.mandatory {
    color: $danger;
}
</style>
