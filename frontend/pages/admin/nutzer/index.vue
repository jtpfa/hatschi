<template>
    <main>
        <h1 class="mb-5">Nutzerverwaltung</h1>

        <h2 class="mb-4">Kunden</h2>
        <data-overview :dashboard="false" :fields="userFields" type="customer"></data-overview>

        <h2>Mitarbeiter</h2>
        <data-overview :dashboard="false" :fields="userFields" type="employee"></data-overview>
    </main>
</template>

<script>
import DataOverview from '~/components/admin/data/overview'

export default {
    components: { DataOverview },
    layout: 'dashboard',
    data() {
        return {
            userFields: [
                { key: 'email', label: 'E-Mail', sortable: true },
                { key: 'firstName', label: 'Vorname', sortable: true },
                { key: 'lastName', label: 'Nachname', sortable: true },
                { key: 'actions', label: '', sortable: false },
            ],
        }
    },
}
</script>

<style scoped></style>
