<template>
    <div>
        <data-overview :fields="fields" type="product"></data-overview>
        <b-button class="float-right" to="/admin/produkte/anlegen" variant="primary">Produkt hinzufügen</b-button>
    </div>
</template>

<script>
import DataOverview from '~/components/admin/data/overview'

export default {
    components: { DataOverview },
    layout: 'admin',
    data() {
        return {
            fields: [
                { key: 'name', label: 'Artikelbezeichnung', sortable: true },
                { key: 'description', label: 'Beschreibung', sortable: true },
                { key: 'details', label: 'Details', sortable: true },
                {
                    key: 'price',
                    label: 'Preis',
                    formatter: price => {
                        return this.$options.filters.currency(this.$currency.insertFractionForEuroConversation(price))
                    },
                    sortable: true,
                },
                { key: 'stock', label: 'Lagerbestand', sortable: true },
                { key: 'actions', label: '', sortable: false },
            ],
        }
    },
}
</script>

<style scoped></style>
