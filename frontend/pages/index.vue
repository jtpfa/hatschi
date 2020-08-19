<template>
    <b-container>
        <home-stage />

        <p class="h3 mb-5">Aktuelle Top-Seller</p>

        <!-- @todo adjust search form styling -->
        <home-search @search="filterProducts($event)" />

        <home-product-overview
            :error="error"
            :products="!filtered ? products : filteredProducts"
            :state="$fetchState.pending"
        />
    </b-container>
</template>

<script>
import HomeProductOverview from '~/components/home/productOverview'
import HomeSearch from '~/components/home/search'
import HomeStage from '~/components/home/stage'

export default {
    components: {
        HomeSearch,
        HomeStage,
        HomeProductOverview,
    },
    async fetch() {
        try {
            this.products = await this.$api.getAllProductsShortVersion()
        } catch (err) {
            this.error = err.message || 'Leider konnten keine Produkte geladen werden.'
        }
    },
    data() {
        return {
            products: [],
            filteredProducts: [],
            filtered: false,
            error: '',
        }
    },
    fetchOnServer: false,
    methods: {
        filterProducts(searchParam) {
            this.filtered = true
            this.filteredProducts = this.products.filter(
                product =>
                    product.name.toLocaleLowerCase().includes(searchParam) ||
                    product.description.toLocaleLowerCase().includes(searchParam)
            )
        },
    },
}
</script>

<style lang="scss" scoped>
p {
    margin-top: 5rem;
}
</style>
