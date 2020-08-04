<template>
    <div v-if="accessGranted">
        <admin-header />
        <div class="d-flex">
            <admin-sidebar />
            <b-container class="my-5 mx-3">
                <keep-alive exclude="data-overview">
                    <nuxt />
                </keep-alive>
            </b-container>
        </div>
    </div>
    <login-form v-else :has-access="accessGranted" :login-page="true" modal-id="modal-login" />
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
