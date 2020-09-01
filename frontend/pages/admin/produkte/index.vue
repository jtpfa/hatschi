<template>
    <main>
        <data-overview :dashboard="false" :fields="fields" type="product" />
        <b-button class="float-right" to="/admin/produkte/anlegen" variant="primary">Produkt hinzufügen</b-button>
    </main>
</template>

<script>
import DataOverview from '~/components/admin/data/overview'

export default {
    components: { DataOverview },
    layout: 'dashboard',
    data() {
        return {
            fields: [
                { key: 'name', label: 'Name', sortable: true },
                { key: 'description', label: 'Beschreibung', sortable: true },
                { key: 'details', label: 'Details', sortable: true },
                {
                    key: 'price',
                    label: 'Preis',
                    formatter: price => {
                        return this.$currencyConverter.convertCentsToEuro(price)
                    },
                    sortable: true,
                },
                { key: 'stock', label: 'Lager', sortable: true },
                { key: 'image', label: 'Bild', sortable: false },
                { key: 'actions', label: '', sortable: false },
            ],
        }
    },
}
</script>

<style scoped></style>
