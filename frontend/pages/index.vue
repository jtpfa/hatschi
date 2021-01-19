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
/**
 * @component IndexPage
 * @desc Index page
 * @lifecycle fetch - Fetch all products in the short version
 * @author Jonas Pfannkuche
 */

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
            /**
             * @member {Array.<Object>} products - All products in short version
             */
            products: [],
            /**
             * @member {Array.<Object>} filteredProducts - Filtered products of {@link component:IndexPage~products products}
             */
            filteredProducts: [],
            /**
             * @member {Boolean} filtered - Filter status (filtered or not)
             */
            filtered: false,
            /**
             * @member {String} error - General error message
             */
            error: '',
        }
    },
    methods: {
        /**
         * @method filterProducts
         * @desc Searches for products which contain the search keyword in their name or description
         * @param {String} searchParam - Search query
         */
        filterProducts(searchParam) {
            this.filtered = true
            this.filteredProducts = this.products.filter(
                product =>
                    product.name.toLocaleLowerCase().includes(searchParam.toLocaleLowerCase()) ||
                    product.description.toLocaleLowerCase().includes(searchParam.toLocaleLowerCase())
            )
        },
        /**
         * @method sortProducts
         * @desc Sorts products ascendig or descending by name or price
         * @param {Object} sortParam - Sort param
         * @param {'name'|'price'} sortParam.key - Field to sort
         * @param {'asc'|'desc'} sortParam.order - Order by
         */
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
