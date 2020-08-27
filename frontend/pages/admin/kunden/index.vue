<template>
    <div>
        <h1 class="mb-5">Nutzerverwaltung</h1>

        <h2 class="mb-4">Kunden</h2>
        <data-overview :dashboard="false" :fields="userFields" type="customer"></data-overview>

        <template v-if="accessGranted">
            <h2>Mitarbeiter</h2>

            <data-overview :dashboard="false" :fields="userFields" type="customer"></data-overview>
        </template>
    </div>
</template>

<script>
import DataOverview from '~/components/admin/data/overview'

export default {
    components: { DataOverview },
    layout: 'admin',
    data() {
        return {
            accessGranted: false,
            userFields: [
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
