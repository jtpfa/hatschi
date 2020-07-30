<template>
    <b-sidebar class="bg-white" lazy no-close-on-route-change no-header shadow visible>
        <div class="sidebar-content px-3 py-2">
            <nav class="mb-3">
                <b-nav vertical>
                    <b-nav-item to="/admin">Dashboard</b-nav-item>
                    <b-nav-item to="/admin/produkte">Produkte</b-nav-item>
                    <b-nav-item to="/admin/kunden">Kunden</b-nav-item>
                </b-nav>
            </nav>
        </div>
    </b-sidebar>
</template>

<script>
export default {
    name: 'AdminSidebar',
}
</script>

<style lang="scss" scoped>
// Override default bootstrap sidebar position
.b-sidebar-outer,
::v-deep .b-sidebar {
    position: relative !important;
}

.b-sidebar-outer {
    height: unset;
    min-height: 100vh;
}

::v-deep .b-sidebar {
    height: 100% !important;
}
</style>
