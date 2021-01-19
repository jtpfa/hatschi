<template>
    <div v-if="hasAccess">
        <dashboard-header />
        <div class="d-flex">
            <dashboard-sidebar />
            <b-container class="my-5 mx-3 overflow-y-hidden">
                <nuxt keep-alive />
            </b-container>
        </div>
    </div>
    <div v-else class="d-flex justify-content-center align-items-center vh-100">
        <login-form :dashboard="true" :login-page="true" />
    </div>
</template>

<script>
/**
 * @component LayoutDashboard
 * @desc Layout for the dashboard
 * @lifecycle mounted - Check if user is allowed to visit admin pages. Redirect to login page if access is forbidden.
 * @author Jonas Pfannkuche
 */

import DashboardHeader from '~/components/dashboard/layout/header'
import DashboardSidebar from '~/components/dashboard/layout/sidebar'
import LoginForm from '~/components/general/login/form'

export default {
    name: 'Dashboard',
    components: { LoginForm, DashboardHeader, DashboardSidebar },
    middleware: ['auth'],
    data() {
        return {
            /**
             * @member {Boolean} hasAccess - Has user privileges to view page
             */
            hasAccess: false,
        }
    },
    mounted() {
        this.hasAccess = this.$auth.$state.roles?.includes('employee') || this.$auth.$state.roles?.includes('admin')

        if (!this.hasAccess) {
            this.$auth.$storage.setUniversal('redirect', '/dashboard')
        } else {
            this.$auth.$storage.removeUniversal('redirect')
        }
    },
    head() {
        return {
            title: 'Dashboard - PC Masterrace',
        }
    },
}
</script>

<style lang="scss" scoped>
.overflow-y-hidden {
    overflow-y: hidden;
}
</style>
