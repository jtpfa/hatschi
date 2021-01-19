<template>
    <footer class="mt-auto py-3 bg-dark">
        <b-navbar class="d-flex flex-column flex-md-row">
            <b-navbar-nav class="mb-3 mb-md-0 ml-md-auto order-md-1">
                <b-nav-item href="/impressum">Impressum</b-nav-item>
                <b-nav-item href="/zahlungsbedingungen">Zahlungsbedingungen</b-nav-item>
                <b-nav-item href="/kontakt">Kontakt</b-nav-item>
            </b-navbar-nav>

            <page-logo class="order-md-0" />
        </b-navbar>
    </footer>
</template>

<script>
/**
 * @component PageFooter
 * @desc Shop footer
 * @author Jonas Pfannkuche
 */

import PageLogo from '~/components/shop/layout/logo'

export default {
    name: 'PageFooter',
    components: { PageLogo },
}
</script>

<style lang="scss" scoped>
footer .navbar-nav .nav-link {
    font-size: 1.2rem;
    color: $white;
}
</style>
