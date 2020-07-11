<template>
    <div>
        <b-navbar toggleable="md" type="light">
            <page-logo />

            <b-navbar-toggle target="nav-collapse" />

            <b-collapse id="nav-collapse" is-nav>
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-item href="#">Warenkorb</b-nav-item>
                    <span v-b-modal.modal-center class="d-flex align-items-center flex-md-column login big-noodle">
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
import PageLogo from '~/components/layout/logo'
import LoginForm from '../login/form'

export default {
    name: 'LayoutHeader',
    components: { PageLogo, LoginForm },
}
</script>

<style lang="scss" scoped>
.login {
    transition: opacity 0.3s ease-in-out;

    &:hover {
        opacity: 0.8;
    }
}
</style>
