<template>
    <fetch-content v-if="$fetchState.pending" :size="6" />
    <div v-else class="grid">
        <product-card
            v-for="item in products"
            :key="item.id"
            image-src="/img/sample.png"
            :name="item.name"
            :price="item.price"
            :text="item.description"
        />
    </div>
</template>

<script>
import FetchContent from '~/components/layout/fetchContent'
import ProductCard from '~/components/product/card'

export default {
    name: 'HomeProductOverview',
    components: { FetchContent, ProductCard },
    async fetch() {
        try {
            this.products = await this.$api.getAllProducts()
        } catch (e) {
            // @todo error hadnling
        }
    },
    data() {
        return {
            products: [],
        }
    },
    fetchOnServer: false,
}
</script>

<style lang="scss" scoped>
.grid {
    display: grid;
    grid-template-columns: 1fr;
    column-gap: map_get($spacers, 3);
    row-gap: map_get($spacers, 3);

    @media (min-width: $grid-sm) {
        grid-template-columns: repeat(2, 1fr);
    }

    @media (min-width: $grid-lg) {
        grid-template-columns: repeat(3, 1fr);
    }

    @media (min-width: $grid-xl) {
        grid-template-columns: repeat(4, 1fr);
    }
}
</style>
