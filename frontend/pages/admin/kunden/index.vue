<template>
    <data-overview
        v-if="accessGranted"
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

    <login-form v-else :has-access="accessGranted" :login-page="true" modal-id="modal-login" />
</template>

<script>
import DataOverview from '~/components/admin/data/overview'
import LoginForm from '~/components/login/form'

export default {
    components: { LoginForm, DataOverview },
    layout: 'admin',
    data() {
        return {
            accessGranted: false,
        }
    },
    mounted() {
        this.accessGranted = this.$auth.$state.roles.includes('admin')

        if (!this.accessGranted) {
            this.$auth.$storage.setCookie('redirect', '/admin/kunden')
        }
    },
}
</script>

<style scoped></style>
