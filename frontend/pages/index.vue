<template>
    <main class="container">
        <home-stage />

        <home-filter class="mb-5 top-seller-filter" @filtered="filterProducts($event)" @sort="sortProducts($event)" />

        <home-product-overview
            :error="error"
            :products="!filtered ? products : filteredProducts"
            :state="$fetchState.pending"
        />
    </main>
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
                    product.name.toLocaleLowerCase().includes(searchParam.toLocaleLowerCase()) ||
                    product.description.toLocaleLowerCase().includes(searchParam).toLocaleLowerCase()
            )
        },
        sortProducts(sortParam) {
            if (!this.filtered) {
                // deep clone the products array
                this.filteredProducts = JSON.parse(JSON.stringify(this.products))
            }
            this.filtered = true
            this.filteredProducts.sort((a, b) => {
                if (sortParam.key === 'price') {
                    if (sortParam.order === 'asc') {
                        return a.price - b.price
                    }

                    return b.price - a.price
                }

                if (sortParam.order === 'asc') {
                    return a.name.toLocaleLowerCase().localeCompare(b.name.toLocaleLowerCase())
                }

                return b.name.toLocaleLowerCase().localeCompare(a.name.toLocaleLowerCase())
            })
        },
    },
}
</script>

<style lang="scss" scoped>
.top-seller-filter {
    margin-top: 2.5rem;

    @media (min-width: $grid-md) {
        margin-top: 4.5rem;
    }
}
</style>
