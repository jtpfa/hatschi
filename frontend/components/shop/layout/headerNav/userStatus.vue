<template>
    <b-nav-item
        v-if="!$auth.loggedIn"
        v-b-modal.modal-center
        link-classes="d-flex align-items-center flex-md-column big-noodle"
    >
        <b-avatar class="mr-2 mr-md-0" variant="primary" />
        Login
    </b-nav-item>
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
/**
 * @component HeaderNavUserStatus
 * @desc Shows a login button when the user isn't logged in. Otherwise a menu with account related actions and pages
 * @author Jonas Pfannkuche
 */

export default {
    name: 'HeaderNavUserStatus',
    methods: {
        /**
         * @method logout - Logs the user out
         */
        logout() {
            this.$auth.logout()
        },
    },
}
</script>

<style lang="scss" scoped></style>
