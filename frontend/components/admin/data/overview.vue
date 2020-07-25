<template>
    <div>
        <b-table
            id="data-table"
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
                <h4 class="text-center text-info">Es gibt noch keine Kunden.</h4>
            </template>
        </b-table>
        <b-pagination
            v-model="currentPage"
            align="center"
            aria-controls="data-table"
            :limit="3"
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
        items: {
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
    data() {
        return {
            perPage: 2,
            currentPage: 1,
            currentItem: {},
            deletionConfirmed: '',
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
            let groupType = ''
            if (this.type === 'customer') {
                groupType = 'Kunde'
            } else if (this.type === 'product') {
                groupType = 'Artikel'
            }
            this.deletionConfirmed = ''
            this.$bvModal
                .msgBoxConfirm(`Soll der ${groupType} wirklich gelöscht werden?`, {
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
                    this.deletionConfirmed = value
                })
                .catch(err => {
                    this.deletionConfirmed = err
                })
        },
    },
}
</script>

<style scoped></style>
