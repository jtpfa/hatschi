<template>
    <div>
        <b-alert :show="fetchErrorMsg.length > 0" variant="warning">{{ fetchErrorMsg }}</b-alert>
        <b-alert :show="error.length > 0" variant="danger">{{ error }}</b-alert>

        <b-table
            :id="`${type}-table`"
            :busy="$fetchState.pending"
            :current-page="currentPage"
            :fields="fields"
            head-variant="primary"
            hover
            :items="items"
            outlined
            :per-page="perPage"
            primary-key="id"
            :responsive="true"
            show-empty
            :sort-by="sortBy"
            @row-hovered="setCurrentItem"
        >
            <template v-slot:cell(image)="row">
                <b-img-lazy
                    :alt="row.item.name"
                    class="image-preview"
                    onerror="this.onerror=null;this.srcset='/img/logo-placeholder.svg';"
                    :src="$imageSrcSet.getImageUrl(row.item.id, 256)"
                    :title="row.item.name"
                />
            </template>

            <template v-if="!dashboard" v-slot:cell(actions)>
                <b-button-group>
                    <b-button
                        v-b-tooltip.hover
                        class="action-button"
                        size="sm"
                        title="Bearbeiten"
                        variant="primary"
                        @click="$bvModal.show(`modal-edit-${type}`)"
                    >
                        <icon-pen />
                    </b-button>
                    <b-button
                        v-b-tooltip.hover
                        class="action-button"
                        size="sm"
                        title="Löschen"
                        variant="danger"
                        @click="showMsgBoxConfirmDeletion"
                    >
                        <icon-trash />
                    </b-button>
                </b-button-group>
            </template>

            <template v-slot:table-busy>
                <div class="text-center text-primary my-2">
                    <b-spinner class="align-middle"></b-spinner>
                    <strong>Lädt...</strong>
                </div>
            </template>

            <template v-slot:empty>
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
import IconPen from '~/components/icons/pen'
import IconTrash from '~/components/icons/trash'

export default {
    name: 'DataOverview',
    components: { IconTrash, IconPen, ProductEdit, CustomerEdit },
    props: {
        dashboard: {
            type: Boolean,
            required: true,
        },
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
            required: true,
            validator(type) {
                // The value must match one of these strings
                return ['product', 'customer'].indexOf(type) !== -1
            },
        },
    },
    async fetch() {
        try {
            if (this.type === 'product') {
                this.items = await this.$api.getAllProductsDetailedVersion(this.$auth.getToken('keycloak'))
            } else if (this.type === 'customer') {
                // @todo getAllCustomers
            } else {
                this.items = []
            }
        } catch (err) {
            this.fetchErrorMsg = err || 'Leider gab es ein Problem beim Laden der Daten. Bitte später erneut versuchen.'
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
            fetchErrorMsg: '',
            error: '',
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
                .catch(() => {
                    this.error = 'Leider gab es ein Problem. Bitte später erneut versuchen.'
                })
        },
        async deleteData() {
            if (this.type === 'product') {
                try {
                    await this.$api.deleteProduct(this.currentItem.id, this.$auth.getToken('keycloak'))
                    this.$router.app.refresh()
                } catch (err) {
                    this.error =
                        `Produkt wurde nicht gelöscht: ${err}` ||
                        'Leider gab es ein Problem beim Löschen. Bitte später erneut versuchen.'
                }
            } else if (this.type === 'customer') {
                try {
                    // @todo delete customer api endpoint
                    this.$router.app.refresh()
                } catch (err) {
                    this.error =
                        `Kunde wurde nicht gelöscht: ${err}` ||
                        'Leider gab es ein Problem beim Löschen. Bitte später erneut versuchen.'
                }
            }
        },
    },
}
</script>

<style lang="scss" scoped>
.image-preview {
    max-width: 6rem;
    max-height: 4rem;
}

.action-button svg {
    width: 1.25rem;
    height: auto;
    fill: $white;
}
</style>
