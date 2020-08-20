<template>
    <div>
        <data-overview :dashboard="false" :fields="fields" type="order" />
    </div>
</template>

<script>
import DataOverview from '~/components/admin/data/overview'

export default {
    name: 'Bestellungen',
    components: { DataOverview },
    layout: 'admin',
    data() {
        return {
            fields: [
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
}
</script>

<style scoped></style>
