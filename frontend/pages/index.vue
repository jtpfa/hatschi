<template>
    <b-container>
        <home-stage />

        <home-filter class="mb-5 top-seller-filter" @filtered="filterProducts($event)" />

        <home-product-overview
            :error="error"
            :products="!filtered ? products : filteredProducts"
            :state="$fetchState.pending"
        />
    </b-container>
</template>
<script>
import HomeProductOverview from '~/components/shop/home/productOverview'
import HomeFilter from '~/components/shop/home/filter'
import HomeStage from '~/components/shop/home/stage'

export default {
    components: {
        HomeStage,
        HomeProductOverview,
        HomeFilter,
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
.top-seller-filter {
    margin-top: 5.5rem;
}
</style>
