<template>
    <data-overview
        :fields="[
            { key: 'firstName', label: 'Vorname', sortable: true },
            { key: 'lastName', label: 'Nachname', sortable: true },
            { key: 'actions', label: '', sortable: false },
        ]"
        :items="[
            { id: 1, firstName: 'Dickerson', lastName: 'Macdonald' },
            { id: 2, firstName: 'Larsen', lastName: 'Shaw' },
            { id: 3, firstName: 'Geneva', lastName: 'Wilson' },
        ]"
        type="customer"
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
