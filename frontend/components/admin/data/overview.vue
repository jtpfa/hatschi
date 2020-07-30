<template>
    <div>
        <b-table
            id="data-table"
            :busy="$fetchState.pending"
            :current-page="currentPage"
            :fields="fields"
            head-variant="primary"
            hover
            :items="items"
            outlined
            :per-page="perPage"
            primary-key="id"
            responsive="true"
            show-empty
            :sort-by="sortBy"
            @row-hovered="setCurrentItem"
        >
            <template v-slot:cell(actions)="row">
                <b-button
                    v-b-tooltip.hover
                    class="mr-1"
                    size="sm"
                    title="Bearbeiten"
                    variant="primary"
                    @click="$bvModal.show(`modal-edit-${type}`)"
                >
                    &#9998;
                </b-button>
                <b-button
                    v-b-tooltip.hover
                    size="sm"
                    title="Löschen"
                    variant="danger"
                    @click="showMsgBoxConfirmDeletion"
                >
                    &#10007;
                </b-button>
            </template>

            <template v-slot:table-busy>
                <div class="text-center text-primary my-2">
                    <b-spinner class="align-middle"></b-spinner>
                    <strong>Lädt...</strong>
                </div>
            </template>

            <template v-slot:empty="scope">
                <h4 class="text-center text-info">Keine Daten vorhanden. Bitte neuen Datensatz erstellen.</h4>
            </template>
        </b-table>
        <b-pagination
            v-model="currentPage"
            align="center"
            aria-controls="data-table"
            :limit="5"
            :per-page="perPage"
            :total-rows="rows"
        ></b-pagination>

        <customer-edit
            v-if="type === 'customer'"
            :customer="Object.assign({}, currentItem)"
            modal-id="modal-edit-customer"
        />

        <product-edit
            v-else-if="type === 'product'"
            modal-id="modal-edit-product"
            :product="Object.assign({}, currentItem)"
        />
    </div>
</template>

<script>
import ProductEdit from '~/components/admin/products/edit'
import CustomerEdit from '~/components/admin/customers/edit'

export default {
    name: 'DataOverview',
    components: { ProductEdit, CustomerEdit },
    props: {
        fields: {
            type: Array,
            required: true,
        },
        sortBy: {
            type: String,
            default: '',
        },
        type: {
            type: String,
            default: '',
        },
    },
    async fetch() {
        try {
            if (this.type === 'product') {
                this.items = await this.$api.getAllProducts()
            } else if (this.type === 'customer') {
                // @todo getAllCustomers
            } else {
                this.items = []
            }
        } catch (e) {
            // @todo error hadnling
        }
    },
    fetchOnServer: false,
    data() {
        return {
            perPage: 5,
            currentPage: 1,
            currentItem: {},
            deletionConfirmed: '',
            items: [],
        }
    },
    computed: {
        rows() {
            return this.items.length
        },
    },
    methods: {
        setCurrentItem(item) {
            this.currentItem = item
        },
        showMsgBoxConfirmDeletion() {
            this.$bvModal
                .msgBoxConfirm(`Soll der Datensatz wirklich gelöscht werden?`, {
                    title: 'Löschen bestätigen',
                    size: 'sm',
                    buttonSize: 'sm',
                    okVariant: 'danger',
                    okTitle: 'JA',
                    cancelTitle: 'NEIN',
                    footerClass: 'p-2',
                    hideHeaderClose: false,
                    centered: true,
                })
                .then(value => {
                    if (value) {
                        this.deleteData()
                    }
                })
                .catch(err => {
                    this.deletionConfirmed = err
                })
        },
        async deleteData() {
            if (this.type === 'product') {
                try {
                    await this.$api.deleteProduct(this.currentItem.id, this.$auth.getToken('keycloak'))
                } catch (e) {
                    // @todo error handling
                }
            } else if (this.type === 'customer') {
                // @todo
            } else {
                // @todo
            }
        },
    },
}
</script>

<style scoped></style>
