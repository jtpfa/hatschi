<template>
    <div>
        <b-alert v-if="fetchErrorMsg.length > 0" :show="true" variant="warning">{{ fetchErrorMsg }}</b-alert>

        <b-alert v-else-if="!addresses" :show="true" variant="warning">Keine Adressen hinterlegt</b-alert>

        <div v-else>
            <b-alert :show="error.length > 0" variant="danger">{{ error }}</b-alert>

            <b-card v-for="(item, index) in addresses" :key="item.id" class="my-3">
                <template v-slot:header>
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0 card-title">Adresse {{ index + 1 }}</h4>
                        <div>
                            <b-button variant="white" @click="deleteAddress(item.id)">
                                <icon-trash class="delete" />
                            </b-button>
                            <b-button variant="white"><icon-pen class="edit" /></b-button>
                        </div>
                    </div>
                </template>

                <div class="row mb-2">
                    <div class="col-sm-3 col-md-4 col-lg-3">
                        <span class="font-weight-bold">Name:</span>
                    </div>
                    <div class="col-sm-9 col-md-8 col-lg-9">
                        <b-card-text text-tag="span">{{ item.firstName }} {{ item.lastName }}</b-card-text>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3 col-md-4 col-lg-3">
                        <span class="font-weight-bold">Anschrift:</span>
                    </div>
                    <div class="col-sm-9 col-md-8 col-lg-9">
                        <b-card-text text-tag="span">
                            {{ item.address }}
                        </b-card-text>
                        <br />
                        <b-card-text text-tag="span">{{ item.zip }} {{ item.city }}</b-card-text>
                        <br />
                        <b-card-text v-if="item.additionalAddress" text-tag="span">
                            {{ item.additionalAddress }}
                        </b-card-text>
                        <br v-if="item.additionalAddress" />
                        <b-card-text text-tag="span">
                            {{ item.country }}
                        </b-card-text>
                    </div>
                </div>
            </b-card>
        </div>
    </div>
</template>

<script>
import IconPen from '~/components/general/icons/pen'
import IconTrash from '~/components/general/icons/trash'

export default {
    name: 'AccountShowAllAdresses',
    components: { IconTrash, IconPen },
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
            addresses: [],
            fetchErrorMsg: '',
            error: '',
        }
    },
    methods: {
        async deleteAddress(event) {
            try {
                await this.$api.deleteCustomerAddress(event, this.$auth.getToken('keycloak'))
                this.$router.app.refresh()
            } catch (err) {
                this.error =
                    `Adresse wurde nicht gelöscht: ${err.message}` ||
                    'Leider gab es ein Problem beim Löschen. Bitte später erneut versuchen.'
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
