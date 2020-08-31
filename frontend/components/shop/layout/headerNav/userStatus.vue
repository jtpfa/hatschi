<template>
    <span
        v-if="!$auth.loggedIn"
        v-b-modal.modal-center
        class="d-flex align-items-center flex-md-column nav-icon nav-link big-noodle text-primary"
    >
        <b-avatar class="mr-2 mr-md-0" variant="primary" />
        Login
    </span>
    <b-nav-item-dropdown v-else no-caret right>
        <template slot="button-content">
            <span class="d-flex align-items-center flex-md-column nav-icon big-noodle text-primary">
                <b-avatar class="mr-2 mr-md-0" variant="primary" />
                Konto
            </span>
        </template>

        <b-dropdown-item to="/profil">Profil</b-dropdown-item>

        <b-dropdown-item to="/bestellungen">Bestellungen</b-dropdown-item>

        <b-dropdown-divider></b-dropdown-divider>

        <b-dropdown-item @click="logout">Abmelden</b-dropdown-item>
    </b-nav-item-dropdown>
</template>

<script>
export default {
    name: 'HeaderNavUserStatus',
    methods: {
        logout() {
            window.location.href = `${
                this.$config.keycloakEndpoint
            }protocol/openid-connect/logout?redirect_uri=${encodeURI(this.$config.baseURL)}`
            this.$auth.reset()
        },
    },
}
</script>

<style lang="scss" scoped></style>
