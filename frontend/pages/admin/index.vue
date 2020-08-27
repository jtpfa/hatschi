<template>
    <div>
        <h1 class="mb-5">Dashboard</h1>

        <h2 class="mb-4">Produkte</h2>
        <data-overview class="mb-5" :dashboard="true" :fields="productFields" type="product"></data-overview>

        <h2>Bestellungen</h2>
        <data-overview class="mb-5" :dashboard="true" :fields="orderFields" type="order"></data-overview>

        <h2>Kunden</h2>
        <data-overview class="mb-5" :dashboard="true" :fields="userFields" type="customer"></data-overview>

        <template v-if="accessGranted">
            <h2>Mitarbeiter</h2>
            <data-overview class="mb-5" :dashboard="true" :fields="userFields" type="employee"></data-overview>
        </template>
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
            accessGranted: false,
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
            userFields: [
                { key: 'firstName', label: 'Vorname', sortable: true },
                { key: 'lastName', label: 'Nachname', sortable: true },
                { key: 'actions', label: '', sortable: false },
            ],
            orderFields: [
                {
                    key: 'orderDate',
                    label: 'Datum',
                    formatter: date => new Date(date),
                    sortable: true,
                },
                { key: 'customerEmail', label: 'Kunde', sortable: true },
                { key: 'paid', label: 'Bezahlt', sortable: true },
                { key: 'orderItems', label: 'Bestellte Ware(n)', sortable: true },
                { key: 'actions', label: '', sortable: false },
            ],
        }
    },
    mounted() {
        this.accessGranted = this.$auth.$state.roles?.includes('admin')
    },
}
</script>

<style scoped></style>
