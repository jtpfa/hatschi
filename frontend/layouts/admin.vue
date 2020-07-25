<template>
    <div>
        <admin-header />
        <div class="d-flex">
            <admin-sidebar />
            <b-container class="my-5 mx-3">
                <nuxt />
            </b-container>
        </div>
    </div>
</template>

<script>
import AdminHeader from '~/components/admin/layout/header'
import AdminSidebar from '~/components/admin/layout/sidebar'

export default {
    name: 'Admin',
    components: { AdminHeader, AdminSidebar },
}
</script>

<style lang="scss" scoped></style>
