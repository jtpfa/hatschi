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
import LoginModal from '~/components/general/login/modal'
import HeaderNavShoppingCart from '~/components/shop/layout/headerNav/shoppingCart'
import HeaderNavUserStatus from '~/components/shop/layout/headerNav/userStatus'
import PageLogo from '~/components/shop/layout/logo'

export default {
    name: 'LayoutHeader',
    components: { HeaderNavUserStatus, HeaderNavShoppingCart, PageLogo, LoginModal },
}
</script>

<style lang="scss" scoped>
.header-nav {
    box-shadow: 0 4px 7px 0 rgba(0, 0, 0, 0.16);
}
</style>
