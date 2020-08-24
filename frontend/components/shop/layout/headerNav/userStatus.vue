<template>
    <span
        v-if="!$auth.loggedIn"
        v-b-modal.modal-center
        class="d-flex align-items-center flex-md-column nav-icon big-noodle text-primary"
    >
        <b-avatar class="mr-2 mr-md-0" variant="primary" />
        Login
    </span>
    <span
        v-else-if="$auth.loggedIn && $route.name === 'profil'"
        class="d-flex align-items-center flex-md-column nav-icon big-noodle text-primary"
        @click="logout"
    >
        <b-avatar class="mr-2 mr-md-0" variant="primary" />
        Abmelden
    </span>
    <b-link v-else class="nav-icon" to="/profil">
        <span class="d-flex align-items-center flex-md-column big-noodle">
            <b-avatar class="mr-2 mr-md-0" variant="primary" />
            Profil
        </span>
    </b-link>
</template>

<script>
export default {
    name: 'HeaderNavUserStatus',
    methods: {
        logout() {
            window.location.href = `${this.$config.keycloakLogoutEndpoint}?redirect_uri=${encodeURI(
                this.$config.baseURL
            )}`
            this.$auth.reset()
        },
    },
}
</script>

<style lang="scss" scoped></style>
