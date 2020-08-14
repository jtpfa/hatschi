<template>
    <fetch-content v-if="state" :size="6" />
    <div v-else class="grid">
        <b-alert :show="products.length === 0" variant="secondary">Keine Produkte gefunden.</b-alert>
        <product-card v-for="item in products" :key="item.id" :product="item" />
    </div>
</template>

<script>
import FetchContent from '~/components/layout/fetchContent'
import ProductCard from '~/components/product/card'

export default {
    name: 'HomeProductOverview',
    components: { FetchContent, ProductCard },
    props: {
        products: {
            type: Array,
            required: true,
        },
        state: {
            type: Boolean,
            required: true,
        },
    },
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
