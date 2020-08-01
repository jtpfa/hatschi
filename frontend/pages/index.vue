<template>
    <b-container>
        <home-stage />

        <p class="h3 mb-5">Aktuelle Top-Seller</p>

        <home-product-overview />
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
}
</script>

<style lang="scss" scoped>
p {
    margin-top: 5rem;
}
</style>
