<template>
    <div>
        <b-navbar toggleable="md" type="light">
            <page-logo />

            <b-navbar-toggle target="nav-collapse" />

            <b-collapse id="nav-collapse" is-nav>
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-link
                        to="warenkorb"
                        class="d-flex align-items-center flex-md-column nav-icon big-noodle mr-0 mr-md-4 my-3 my-md-0"
                    >
                        <div class="bg-primary rounded-circle shopping-cart b-avatar badge-primary mr-2 mr-md-0">
                            <icon-shopping-cart />
                        </div>
                        Warenkorb
                    </b-link>
                    <span
                        v-b-modal.modal-center
                        class="d-flex align-items-center flex-md-column nav-icon big-noodle text-primary"
                    >
                        <b-avatar class="mr-2 mr-md-0" variant="primary" />
                        Login
                    </span>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <login-form modal-id="modal-center" />
    </div>
</template>

<script>
import IconShoppingCart from '~/components/icons/shoppingCart'
import PageLogo from '~/components/layout/logo'
import LoginForm from '../login/form'

export default {
    name: 'LayoutHeader',
    components: { IconShoppingCart, PageLogo, LoginForm },
}
</script>

<style lang="scss" scoped>
.nav-icon {
    text-decoration: none;
    transition: opacity 0.3s ease-in-out;

    &:hover {
        color: $primary;
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
