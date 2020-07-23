<template>
    <b-container>
        <home-stage />

        <p class="h3">Aktuelle Top-Seller</p>

        <home-product-overview />
        <h1>{{ status }}</h1>
    </b-container>
</template>

<script>
import HomeProductOverview from '~/components/home/productOverview'
import HomeStage from '~/components/home/stage'

export default {
    components: {
        HomeStage,
        HomeProductOverview,
    },
    computed: {
        status() {
            return this.$auth.loggedIn ? this.$auth.user : 'No user logged in'
        },
    },
}
</script>

<style></style>
