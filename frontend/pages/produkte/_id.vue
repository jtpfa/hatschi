<template>
    <fetch-content v-if="$fetchState.pending" :size="6" />
    <main v-else class="container my-5">
        <b-alert v-if="error.length > 0" :show="true">{{ error }}</b-alert>
        <template v-else>
            <product-details class="my-5" :product="product" />

            <p class="h5">Weitere Produkte</p>

            <div class="grid">
                <product-card v-for="item in recommendations" :key="item.id" :product="item" />
            </div>
        </template>
    </main>
</template>

<script>
/**
 * @component ProductPage
 * @desc Dynamic product page
 * @lifecyle fetch - Fetch product of the given route id and some recommendations
 * @author Jonas Pfannkuche
 */

import FetchContent from '~/components/shop/layout/fetchContent'
import ProductCard from '~/components/shop/product/card'
import ProductDetails from '~/components/shop/product/details'

export default {
    components: { ProductDetails, ProductCard, FetchContent },
    async fetch() {
        try {
            this.product = await this.$api.getProduct(this.$route.params.id)
            this.recommendations = await this.$api.getRandomProducts(this.$route.params.id, 5)
        } catch (err) {
            this.error = err.message || 'Dieses Produkt existiert nicht.'
        }

        if (!this.product.id) {
            this.error = 'Dieses Produkt existiert nicht.'
        }
    },
    data() {
        return {
            /**
             * @member {Object} product - Fetched product
             */
            product: {},
            /**
             * @member {Array.<Object>} recommendations - Recommendations to show on page
             */
            recommendations: [],
            /**
             * @member {String} error - General error message
             */
            error: '',
        }
    },
    head() {
        return {
            title: `${this.product.name ? this.product.name : this.error} – PC Masterrace`,
        }
    },
}
</script>

<style lang="scss" scoped>
.grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    column-gap: map_get($spacers, 3);
    row-gap: map_get($spacers, 3);

    @media (min-width: $grid-md) {
        grid-template-columns: repeat(3, 1fr);
    }

    @media (min-width: $grid-lg) {
        grid-template-columns: repeat(4, 1fr);
    }

    @media (min-width: $grid-xl) {
        grid-template-columns: repeat(5, 1fr);
    }
}
</style>
