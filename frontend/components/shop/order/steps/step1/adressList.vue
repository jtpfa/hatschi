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
/**
 * @component OrderAdressList
 * @desc Radio group of all addresses of the given type with ability to add a new one (redirects to profil)
 * @author Jonas Pfannkuche
 */

export default {
    name: 'OrderAdressList',
    props: {
        /**
         * @vprop {'shipping'|'invoice'} - Type of the address
         */
        addressType: {
            type: String,
            required: true,
            validator(type) {
                // The value must match one of these strings
                return ['shipping', 'invoice'].indexOf(type) !== -1
            },
        },
        /**
         * @vprop {String} fetchErrorMsg - Error message of rejected fetch
         */
        fetchErrorMsg: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            /**
             * @member {Boolean|Function} [validState='null'] - Set to null to prevent validation when user navigates between steps
             */
            validState: null,
        }
    },
    computed: {
        /**
         * @computed {Object} selectedAddress - Selected address
         */
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
        /**
         * @computed {Array.<Object>} addresses - All addresses of the user
         */
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
