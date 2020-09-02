<template>
    <div>
        <h2>
            {{ addressType === 'shipping' ? 'Lieferadresse' : 'Rechnungsadresse' }} auswählen
            <span class="mandatory">*</span>
        </h2>

        <b-alert v-if="fetchErrorMsg.length > 0" :show="true" variant="warning" v-html="fetchErrorMsg" />

        <b-list-group v-else>
            <b-list-group-item v-for="(item, index) in addresses" :key="item.id">
                <b-form-radio
                    v-model="selectedAddress"
                    :name="`${addressType}-address`"
                    required
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
            </b-list-group-item>
            <b-list-group-item class="d-none">
                <b-form-radio class="d-none" :name="`${addressType}-address`" required :state="validState" />
            </b-list-group-item>
        </b-list-group>

        <b-button class="my-4" to="/profil" variant="primary">
            {{ addresses.length === 0 ? 'Neue Adresse hinzufügen' : 'Adressen verwalten' }}
        </b-button>
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
        fetchErrorMsg: {
            type: String,
            default: '',
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
