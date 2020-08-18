<template>
    <client-only>
        <div>
            <b-navbar class="bg-white header-nav" toggleable="md" type="light">
                <page-logo />

                <b-navbar-toggle target="nav-collapse" />

                <b-collapse id="nav-collapse" is-nav>
                    <!-- Right aligned nav items -->
                    <b-navbar-nav class="ml-auto mb-3 mb-md-0">
                        <header-nav-shopping-cart />
                        <header-nav-user-status />
                    </b-navbar-nav>
                </b-collapse>
            </b-navbar>
            <login-modal modal-id="modal-center" />
        </div>
    </client-only>
</template>

<script>
import HeaderNavShoppingCart from '~/components/layout/headerNav/shoppingCart'
import PageLogo from '~/components/layout/logo'
import HeaderNavUserStatus from '~/components/layout/headerNav/userStatus'
import LoginModal from '~/components/login/modal'

export default {
    name: 'LayoutHeader',
    components: { HeaderNavShoppingCart, HeaderNavUserStatus, LoginModal, PageLogo },
}
</script>

<style lang="scss" scoped>
.header-nav {
    box-shadow: 0 4px 7px 0 rgba(0, 0, 0, 0.16);
}
</style>
