<template>
    <fetch-content v-if="$fetchState.pending" :size="6" />
    <div v-else>
        <b-alert v-if="fetchErrorMsg.length > 0" :show="true" variant="warning" v-html="fetchErrorMsg" />

        <b-alert v-else-if="addresses.length === 0" :show="true" variant="warning">
            Keine Adressen hinterlegt. Lege deine erste Adresse an:
        </b-alert>

        <div v-else>
            <b-alert :show="error.length > 0" variant="danger" v-html="error" />

            <b-card v-for="(item, index) in addresses" :key="item.id" class="my-3">
                <template v-slot:header>
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0 card-title">Adresse {{ index + 1 }}</h4>
                        <div>
                            <b-button variant="white" @click="deleteAddress(item.id)">
                                <icon-trash class="delete" />
                            </b-button>
                            <b-button v-b-toggle="`overview-${item.id}`" variant="white">
                                <icon-pen class="edit" />
                            </b-button>
                        </div>
                    </div>
                </template>

                <b-collapse :id="`overview-${item.id}`" visible>
                    <account-address-details :address="item" />
                </b-collapse>

                <b-collapse :id="`overview-${item.id}`">
                    <account-edit-address :address="Object.assign({}, item)" />
                </b-collapse>
            </b-card>
        </div>
    </div>
</template>

<script>
/**
 * @component AccountShowAllAdresses
 * @desc List of all addresses related to the user with the ability to delete and edit each of them
 * @lifecycle fetch - Fetch all addresses of logged in user
 * @author Jonas Pfannkuche
 */

import IconPen from '~/components/general/icons/pen'
import IconTrash from '~/components/general/icons/trash'
import AccountAddressDetails from '~/components/shop/account/address/details'
import AccountEditAddress from '~/components/shop/account/address/edit'
import FetchContent from '~/components/shop/layout/fetchContent'

export default {
    name: 'AccountShowAllAdresses',
    components: { FetchContent, AccountEditAddress, AccountAddressDetails, IconTrash, IconPen },
    async fetch() {
        try {
            this.addresses = await this.$api.getAddressesOfCustomer(this.$auth.getToken('keycloak'))
        } catch (err) {
            this.fetchErrorMsg =
                err.message || 'Leider gab es ein Problem beim Laden der Daten. Bitte später erneut versuchen.'
        }
    },
    data() {
        return {
            /**
             * @vprop {Array.<Object>} addresses - All addresses of the user
             */
            addresses: [],
            /**
             * @member {String} fetchErrorMsg - Error message of rejected fetch
             */
            fetchErrorMsg: '',
            /**
             * @member {String} error - General error message
             */
            error: '',
        }
    },
    methods: {
        /**
         * @method deleteAddress
         * @desc Calls api endpoint to delete address, handles response and resets the addresses of the order saved in the store to prevent null errors
         * @param {Number} event - Event which holds the id of the address that should be deleted
         * @returns {Promise<void>}
         */
        async deleteAddress(event) {
            try {
                await this.$api.deleteCustomerAddress(event, this.$auth.getToken('keycloak'))
                this.$store.commit('order/updateOrderInformation', {
                    key: 'shippingAddress',
                    data: { id: -1, index: -1 },
                })
                this.$store.commit('order/updateOrderInformation', {
                    key: 'invoiceAddress',
                    data: { id: -1, index: -1 },
                })
                this.$router.app.refresh()
            } catch (err) {
                this.error = err.message || 'Leider gab es ein Problem beim Löschen. Bitte später erneut versuchen.'
            }
        },
    },
}
</script>

<style lang="scss" scoped>
.card-header {
    padding-right: 0.25rem;

    svg.edit,
    svg.delete {
        min-width: 1rem;
        width: 1rem;
        fill: $primary;
        transition: 0.3s opacity ease-in-out;
    }

    button:hover svg {
        opacity: 0.6;
    }
}
</style>
