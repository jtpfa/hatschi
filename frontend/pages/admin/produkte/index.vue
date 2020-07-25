<template>
    <data-overview
        :fields="[
            { key: 'name', label: 'Artikelbezeichnung', sortable: true },
            { key: 'description', label: 'Beschreibung', sortable: true },
            { key: 'price', label: 'Preis', sortable: true },
            { key: 'stock', label: 'Lagerbestand', sortable: true },
            { key: 'actions', label: '', sortable: false },
        ]"
        :items="[
            {
                id: 1,
                name: 'Testartikel1',
                description: 'Lorem ipsum dolor sit erat dies das ananas',
                price: 5000,
                stock: 10,
            },
            {
                id: 2,
                name: 'Testartikel2',
                description: 'Lorem ipsum dolor sit erat dies das ananas',
                price: 10000,
                stock: 3,
            },
            {
                id: 3,
                name: 'Testartikel3',
                description: 'Lorem ipsum dolor sit erat dies das ananas',
                price: 15000,
                stock: 4,
            },
        ]"
        type="product"
    ></data-overview>
</template>

<script>
import DataOverview from '~/components/admin/data/overview'

export default {
    components: { DataOverview },
    layout: 'admin',
}
</script>

<style scoped></style>
