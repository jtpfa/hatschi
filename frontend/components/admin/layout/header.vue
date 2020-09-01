<template>
    <div>
        <b-navbar class="d-flex justify-content-between align-items-center" sticky type="dark" variant="primary">
            <b-navbar-brand to="/admin">Admin Panel</b-navbar-brand>
            <b-link class="text-light" @click="logout">Abmelden</b-link>
        </b-navbar>
    </div>
</template>

<script>
export default {
    name: 'AdminHeader',
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
