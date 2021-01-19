<template>
    <div>
        <b-navbar class="d-flex justify-content-between align-items-center" sticky type="dark" variant="primary">
            <b-navbar-brand to="/admin">Admin Panel</b-navbar-brand>
            <b-link class="text-light" @click="logout">Abmelden</b-link>
        </b-navbar>
    </div>
</template>

<script>
/**
 * @component DashboardHeader
 * @desc Header of dashboard
 * @author Jonas Pfannkuche
 */
export default {
    name: 'DashboardHeader',
    methods: {
        /**
         * @method logout
         * @desc Logs the user out
         */
        logout() {
            this.$auth.logout()
        },
    },
}
</script>

<style lang="scss" scoped></style>
