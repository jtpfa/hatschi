<template>
    <main>
        <h1 class="mb-5">Nutzerverwaltung</h1>

        <h2 class="mb-4">Kunden</h2>
        <data-overview :dashboard="false" :fields="userFields" type="customer"></data-overview>

        <h2>Mitarbeiter</h2>
        <data-overview :dashboard="false" :fields="userFields" type="employee"></data-overview>

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
            userFields: [
                { key: 'email', label: 'E-Mail', sortable: true },
                { key: 'firstName', label: 'Vorname', sortable: true },
                { key: 'lastName', label: 'Nachname', sortable: true },
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
