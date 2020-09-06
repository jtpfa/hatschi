<template>
    <main>
        <data-overview :dashboard="false" :fields="fields" type="product" />
        <b-button class="float-right" to="/admin/produkte/anlegen" variant="primary">Produkt hinzufügen</b-button>
    </main>
</template>

<script>
/**
 * @component DashboardProductIndexPage
 * @desc Dashboard product page
 * @author Jonas Pfannkuche
 */

import DataOverview from '~/components/dashboard/data/overview'

export default {
    components: { DataOverview },
    layout: 'dashboard',
    data() {
        return {
            /**
             * @member {Array} fields - Fields that should be shown in the table
             */
            fields: [
                { key: 'id', label: 'Produktnr.', sortable: true },
                { key: 'name', label: 'Name', sortable: true },
                { key: 'description', label: 'Beschreibung', sortable: true },
                {
                    key: 'details',
                    label: 'Details',
                    formatter: details => this.$textCropper.cropText(details, 100),
                    sortable: true,
                },
                {
                    key: 'price',
                    label: 'Preis',
                    formatter: price => this.$currencyConverter.convertCentsToEuro(price),
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
