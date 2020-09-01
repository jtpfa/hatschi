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

            <template v-if="!dashboard" v-slot:cell(actions)="row">
                <b-button-group class="float-right">
                    <b-button
                        v-if="type !== 'order' && userIsAllowedToEdit"
                        class="action-button"
                        size="sm"
                        variant="primary"
                        @click="showEditModal(row.item)"
                    >
                        <icon-pen />
                    </b-button>
                    <b-button
                        v-if="type !== 'order' && userIsAllowedToDelete"
                        class="action-button"
                        size="sm"
                        variant="danger"
                        @click="confirmDeletion(row.item)"
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
                <h4 class="text-center text-info">Keine Daten vorhanden.</h4>
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

        <customer-edit v-if="type === 'customer'" :customer="currentItem" modal-id="modal-edit-customer" />

        <employee-edit
            v-else-if="['employee', 'admin'].includes(type)"
            :employee="currentItem"
            :modal-id="`modal-edit-${type}`"
            :role="type"
        />

        <product-edit v-else-if="type === 'product'" modal-id="modal-edit-product" :product="currentItem" />
    </div>
</template>

<script>
import EmployeeEdit from '~/components/admin/employee/edit'
import ProductEdit from '~/components/admin/products/edit'
import CustomerEdit from '~/components/admin/customers/edit'
import IconPen from '~/components/general/icons/pen'
import IconTrash from '~/components/general/icons/trash'

export default {
    name: 'DataOverview',
    components: { EmployeeEdit, IconTrash, IconPen, ProductEdit, CustomerEdit },
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
                return ['product', 'customer', 'employee', 'admin', 'order'].indexOf(type) !== -1
            },
        },
    },
    async fetch() {
        try {
            switch (this.type) {
                case 'product':
                    this.items = await this.$api.getAllProductsDetailedVersion(this.$auth.getToken('keycloak'))
                    break
                case 'customer':
                    this.items = await this.$api.getCustomers(this.$auth.getToken('keycloak'))
                    break
                case 'employee':
                    this.items = await this.$api.getEmployees(this.$auth.getToken('keycloak'))
                    break
                case 'admin':
                    this.items = await this.$api.getAdmins(this.$auth.getToken('keycloak'))
                    break
                case 'order':
                    this.items = await this.$api.getAllOrders(this.$auth.getToken('keycloak'))
                    break
                default:
                    this.items = []
                    break
            }
        } catch (err) {
            this.fetchErrorMsg =
                err.message || 'Leider gab es ein Problem beim Laden der Daten. Bitte später erneut versuchen.'
        }
    },
    data() {
        return {
            userIsAllowedToEdit: false,
            userIsAllowedToDelete: false,
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
    mounted() {
        this.userIsAllowedToEdit =
            ['product', 'customer'].includes(this.type) ||
            (['employee', 'admin'].includes(this.type) && this.$auth.$state.roles?.includes('admin'))

        this.userIsAllowedToDelete =
            this.type === 'product' ||
            (['customer', 'employee', 'admin'].includes(this.type) && this.$auth.$state.roles?.includes('admin'))
    },
    activated() {
        // Call fetch again if last fetch more than 30 sec ago
        if (this.$fetchState.timestamp <= Date.now() - 30000) {
            this.$fetch()
        }
    },
    methods: {
        showEditModal(item) {
            this.currentItem = { ...item }
            this.$bvModal.show(`modal-edit-${this.type}`)
        },
        confirmDeletion(item) {
            this.currentItem = { ...item }
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
                .then(async value => {
                    if (value) {
                        await this.deleteData(this.currentItem)
                    }
                })
                .catch(err => {
                    this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
                })
        },
        async deleteData(item) {
            if (this.type === 'product') {
                try {
                    await this.$api.deleteProduct(item.id, this.$auth.getToken('keycloak'))
                    this.$router.app.refresh()
                } catch (err) {
                    this.error =
                        `Produkt wurde nicht gelöscht: ${err.message}` ||
                        'Leider gab es ein Problem beim Löschen. Bitte später erneut versuchen.'
                }
            } else if (['customer', 'employee', 'admin'].includes(this.type)) {
                try {
                    await this.$api.deleteUser(item.email, this.type, this.$auth.getToken('keycloak'))
                    this.$router.app.refresh()
                } catch (err) {
                    this.error =
                        `Kunde wurde nicht gelöscht: ${err.message}` ||
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

::v-deep table td {
    white-space: pre-line;
}
</style>
