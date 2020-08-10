<template>
    <div class="d-flex flex-column page">
        <page-header />
        <nuxt class="mb-5" />
        <page-footer />
    </div>
</template>

<script>
import PageFooter from '~/components/layout/footer'
import PageHeader from '~/components/layout/header'

export default {
    components: {
        PageFooter,
        PageHeader,
    },
}
</script>

<style lang="scss" scoped>
.page {
    height: 100vh;
}
</style>
