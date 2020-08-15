<template>
    <div>
        <h1 class="mb-5">Dashboard</h1>

        <h2 class="mb-4">Produkte</h2>
        <data-overview :fields="productFields" type="product"></data-overview>

        <h2>Kunden</h2>
        <data-overview :fields="customerFields" type="customer"></data-overview>
    </div>
</template>

<script>
import DataOverview from '~/components/admin/data/overview'

export default {
    name: 'Index',
    components: { DataOverview },
    layout: 'admin',
    data() {
        return {
            productFields: [
                { key: 'name', label: 'Artikelbezeichnung', sortable: true },
                { key: 'description', label: 'Beschreibung', sortable: true },
                { key: 'details', label: 'Details', sortable: true },
                {
                    key: 'price',
                    label: 'Preis',
                    formatter: price => {
                        return this.$options.filters.currency(
                            this.$currencyConverter.insertFractionForEuroConversion(price)
                        )
                    },
                    sortable: true,
                },
                { key: 'stock', label: 'Lagerbestand', sortable: true },
                { key: 'image', label: 'Bild', sortable: false },
                { key: 'actions', label: '', sortable: false },
            ],
            customerFields: [
                { key: 'firstName', label: 'Vorname', sortable: true },
                { key: 'lastName', label: 'Nachname', sortable: true },
                { key: 'actions', label: '', sortable: false },
            ],
        }
    },
}
</script>

<style scoped></style>
