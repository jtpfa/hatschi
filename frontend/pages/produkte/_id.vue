<template>
    <fetch-content v-if="$fetchState.pending" :size="6" />
    <main v-else class="container my-5">
        <b-alert v-if="error.length > 0" :show="true">{{ error }}</b-alert>
        <template v-else>
            <product-details class="my-5" :product="product" />

            <p class="h5">Weitere Produkte</p>

            <div class="grid">
                <product-card
                    v-for="i in 5"
                    :key="i"
                    :product="{
                        id: i,
                        name: 'Feinste Ware',
                        description:
                            'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et\n' +
                            '            dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip',
                        price: 299.99,
                    }"
                />
            </div>
        </template>
    </main>
</template>

<script>
import FetchContent from '~/components/shop/layout/fetchContent'
import ProductCard from '~/components/shop/product/card'
import ProductDetails from '~/components/shop/product/details'

export default {
    name: 'Product',
    components: { ProductDetails, ProductCard, FetchContent },
    async fetch() {
        try {
            this.product = await this.$api.getProduct(this.$route.params.id)
        } catch (err) {
            this.error = err.message || 'Dieses Produkt existiert nicht.'
        }

        if (!this.product.id) {
            this.error = 'Dieses Produkt existiert nicht.'
        }
    },
    data() {
        return {
            product: {},
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
