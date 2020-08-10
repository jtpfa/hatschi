<template>
    <div v-if="accessGranted">
        <admin-header />
        <div class="d-flex">
            <admin-sidebar />
            <b-container class="my-5 mx-3">
                <nuxt />
            </b-container>
        </div>
    </div>
    <div v-else class="d-flex justify-content-center align-items-center vh-100">
        <login-form :has-access="accessGranted" :login-page="true" />
    </div>
</template>

<script>
import AdminHeader from '~/components/admin/layout/header'
import AdminSidebar from '~/components/admin/layout/sidebar'
import LoginForm from '~/components/login/form'

export default {
    name: 'Admin',
    components: { LoginForm, AdminHeader, AdminSidebar },
    middleware: ['auth'],
    data() {
        return {
            accessGranted: false,
        }
    },
    mounted() {
        this.accessGranted = this.$auth.$state.roles.includes('employee') || this.$auth.$state.roles.includes('admin')

        if (!this.accessGranted) {
            this.$auth.$storage.setCookie('redirect', '/admin')
        }
    },
}
</script>

<style lang="scss" scoped></style>
