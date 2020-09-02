<template>
    <main>
        <h1 class="mb-5">Dashboard</h1>

        <h2 class="mb-4">Produkte</h2>
        <data-overview class="mb-5" :dashboard="true" :fields="productFields" type="product"></data-overview>

        <h2>Bestellungen</h2>
        <data-overview
            class="mb-5"
            :dashboard="true"
            :fields="orderFields"
            sort-by="orderDate"
            sort-desc
            type="order"
        ></data-overview>

        <h2>Kunden</h2>
        <data-overview class="mb-5" :dashboard="true" :fields="userFields" type="customer"></data-overview>

        <h2>Mitarbeiter</h2>
        <data-overview class="mb-5" :dashboard="true" :fields="userFields" type="employee"></data-overview>

        <template v-if="accessGranted">
            <h2>Admins</h2>
            <data-overview :dashboard="false" :fields="userFields" type="admin"></data-overview>
        </template>
    </main>
</template>

<script>
import DataOverview from '~/components/admin/data/overview'

export default {
    components: { DataOverview },
    layout: 'dashboard',
    data() {
        return {
            accessGranted: false,
            productFields: [
                { key: 'name', label: 'Artikelbezeichnung', sortable: true },
                {
                    key: 'price',
                    label: 'Preis',
                    formatter: price => {
                        return this.$currencyConverter.convertCentsToEuro(price)
                    },
                    sortable: true,
                },
                { key: 'stock', label: 'Lagerbestand', sortable: true },
                { key: 'image', label: 'Bild', sortable: false },
            ],
            userFields: [
                { key: 'email', label: 'E-Mail', sortable: true },
                { key: 'firstName', label: 'Vorname', sortable: true },
                { key: 'lastName', label: 'Nachname', sortable: true },
            ],
            orderFields: [
                {
                    key: 'orderDate',
                    label: 'Datum',
                    formatter: date => this.$dateFormatter.toDateString(date),
                    sortable: true,
                },
                { key: 'id', label: 'Bestellnr.', sortable: true },
                {
                    key: 'orderStatus',
                    label: 'Status',
                    formatter: status => (status === 'OPEN' ? 'Offen' : 'Geliefert'),
                    sortable: true,
                },
                { key: 'customerEmail', label: 'Kunde', sortable: true },
                {
                    key: 'paid',
                    label: 'Bezahlt',
                    formatter: paid => (paid ? 'Ja' : 'Nein'),
                    sortable: true,
                },
            ],
        }
    },
    mounted() {
        this.accessGranted = this.$auth.$state.roles?.includes('admin')
    },
}
</script>

<style scoped></style>
