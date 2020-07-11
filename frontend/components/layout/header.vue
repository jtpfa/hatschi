<template>
    <div>
        <b-navbar toggleable="md" type="light">
            <b-navbar-brand href="/">
                <icon-logo />
            </b-navbar-brand>

            <b-navbar-toggle target="nav-collapse" />

            <b-collapse id="nav-collapse" is-nav>
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-item href="#">Warenkorb</b-nav-item>
                    <span v-b-modal.modal-center class="d-flex align-items-center flex-md-column login">
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
import IconLogo from '../icons/logo'
import LoginForm from '../login/form'

export default {
    name: 'LayoutHeader',
    components: { LoginForm, IconLogo },
}
</script>

<style lang="scss" scoped>
.navbar-brand {
    svg {
        width: 5rem;
        height: auto;
        transition: all 0.3s ease-in-out;
    }

    &:hover svg {
        transform: scale(1.05);
    }
}

.login {
    font-family: 'BigNoodle', sans-serif;
    transition: opacity 0.3s ease-in-out;

    &:hover {
        opacity: 0.8;
    }
}
</style>
