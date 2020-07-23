<template>
    <b-container class="my-5">
        <h1>{{ id }}</h1>
        <product-short-description class="my-5" :product="{}" />

        <product-details class="my-5" />

        <p class="h5">Ähnliche Produkte</p>

        <div class="grid">
            <template v-for="i in 5">
                <product-card
                    image-src="/img/sample.png"
                    name="Feinste Ware"
                    :price="299.99"
                    text="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip"
                />
            </template>
        </div>
    </b-container>
</template>

<script>
import productCard from '~/components/product/card'
import productDetails from '~/components/product/details'
import productShortDescription from '~/components/product/shortDescription'

export default {
    name: 'Product',
    components: { productCard, productDetails, productShortDescription },
    data() {
        return {
            id: this.$route.params.id,
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
