<template>
    <div v-if="this.$auth.scope === 'employee' || this.$auth.scope === 'admin'">
        <admin-header />
        <div class="d-flex">
            <admin-sidebar />
            <b-container class="my-5 mx-3">
                <nuxt />
            </b-container>
        </div>
    </div>
    <login-form v-else :login-page="true" modal-id="modal-login" />
</template>

<script>
import AdminHeader from '~/components/admin/layout/header'
import AdminSidebar from '~/components/admin/layout/sidebar'
import LoginForm from '~/components/login/form'

export default {
    name: 'Admin',
    components: { LoginForm, AdminHeader, AdminSidebar },
    middleware: ['auth'],
}
</script>

<style lang="scss" scoped></style>
