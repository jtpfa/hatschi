<template>
    <div>
        <b-navbar class="bg-white header-nav" toggleable="md" type="light">
            <page-logo />

            <b-navbar-toggle target="nav-collapse" />

            <b-collapse id="nav-collapse" is-nav>
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto mb-3 mb-md-0">
                    <b-link
                        class="d-flex align-items-center flex-md-column nav-icon big-noodle mr-0 mr-md-4 my-3 my-md-0"
                        to="warenkorb"
                    >
                        <div class="bg-primary rounded-circle shopping-cart b-avatar badge-primary mr-2 mr-md-0">
                            <icon-shopping-cart />
                        </div>
                        Warenkorb {{ cartElements }}
                    </b-link>
                    <span
                        v-if="!this.$auth.loggedIn"
                        v-b-modal.modal-center
                        class="d-flex align-items-center flex-md-column nav-icon big-noodle text-primary"
                    >
                        <b-avatar class="mr-2 mr-md-0" variant="primary" />
                        Login
                    </span>
                    <b-link v-else class="nav-icon" to="profil">
                        <span class="d-flex align-items-center flex-md-column big-noodle">
                            <b-avatar class="mr-2 mr-md-0" variant="primary" />
                            Profil
                        </span>
                    </b-link>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <login-form modal-id="modal-center" />
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import IconShoppingCart from '~/components/icons/shoppingCart'
import PageLogo from '~/components/layout/logo'
import LoginForm from '../login/form'

export default {
    name: 'LayoutHeader',
    components: { IconShoppingCart, PageLogo, LoginForm },
    computed: {
        ...mapGetters(['cartElements']),
    },
}
</script>

<style lang="scss" scoped>
.header-nav {
    box-shadow: 0 4px 7px 0 rgba(0, 0, 0, 0.16);
}

.nav-icon {
    text-decoration: none;
    transition: opacity 0.3s ease-in-out;

    &:hover {
        color: $primary;
        text-decoration: none;
        opacity: 0.8;
    }
}

.shopping-cart {
    width: 2.5em;
    height: 2.5em;

    svg {
        fill: $white;
        width: 1.25em;
        height: 1.25em;
    }
}
</style>
