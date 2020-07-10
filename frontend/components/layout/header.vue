<template>
    <div>
        <b-navbar toggleable="md" type="light">
            <b-navbar-brand href="/"><icon-logo/></b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-item href="#">Warenkorb</b-nav-item>
                    <b-nav-item href="#">Konto</b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
  import IconLogo from '../icons/logo'
  export default {
    name: 'layoutHeader',
    components: { IconLogo }
  }
</script>

<style lang="scss" scoped>
.navbar-brand {
    svg {
        width: 5rem;
        height: auto;
    }
}
</style>