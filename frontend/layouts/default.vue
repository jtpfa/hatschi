<template>
    <div class="d-flex flex-column page">
        <page-header />
        <nuxt class="mb-5" />
        <CookieControl locale="de" />
        <page-footer />
    </div>
</template>

<script>
import PageFooter from '~/components/shop/layout/footer'
import PageHeader from '~/components/shop/layout/header'

export default {
    name: 'Default',
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
