<template>
    <fetch-content v-if="$fetchState.pending" :size="6" />
    <b-container v-else class="my-5">
        <b-alert v-if="noProductFound" :show="true">Dieses Produkt existiert nicht.</b-alert>
        <template v-else>
            <product-short-description class="my-5" :product="product" />

            <product-details class="my-5" :product="product" />

            <p class="h5">Ähnliche Produkte</p>

            <div class="grid">
                <product-card
                    v-for="i in 5"
                    :key="i"
                    description="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                    image-src="/img/sample.png"
                    name="Feinste Ware"
                    :price="299.99"
                    :product-id="i"
                />
            </div>
        </template>
    </b-container>
</template>

<script>
import FetchContent from '~/components/layout/fetchContent'
import productCard from '~/components/product/card'
import productDetails from '~/components/product/details'
import productShortDescription from '~/components/product/shortDescription'

export default {
    name: 'Product',
    components: { FetchContent, productCard, productDetails, productShortDescription },
    async fetch() {
        try {
            this.product = await this.$api.getProduct(this.$route.params.id)
        } catch (err) {
            this.noProductFound = true
        }

        if (!this.product.id) {
            this.noProductFound = true
        }
    },
    data() {
        return {
            product: {},
            noProductFound: false,
        }
    },
    head() {
        return {
            title: `PC Masterrace – ${this.product.name}`,
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
