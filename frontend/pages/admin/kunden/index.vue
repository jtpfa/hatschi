<template>
    <data-overview
        v-if="accessGranted"
        :dashboard="false"
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
    data() {
        return {
            accessGranted: false,
        }
    },
    mounted() {
        this.accessGranted = this.$auth.$state.roles?.includes('admin')

        if (!this.accessGranted) {
            this.$auth.$storage.setUniversal('redirect', '/admin/kunden')
            this.$router.push('/auth/login')
        } else {
            this.$auth.$storage.removeUniversal('redirect')
        }
    },
}
</script>

<style scoped></style>
