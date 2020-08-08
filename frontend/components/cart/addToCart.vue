<template>
    <b-button
        class="d-flex justify-content-center align-items-center px-5 py-1"
        :disabled="!orderable"
        variant="primary"
        @click="addToCart"
    >
        <icon-shopping-cart class="mr-3" />
        In den Warenkorb
    </b-button>
</template>

<script>
import IconShoppingCart from '~/components/icons/shoppingCart'

export default {
    name: 'AddToCart',
    components: { IconShoppingCart },
    props: {
        productId: {
            type: Number,
            required: true,
        },
        orderable: {
            type: Boolean,
            required: true,
        },
    },
    methods: {
        addToCart() {
            let item = this.product
            item = {
                ...item,
                id: this.productId,
                quantity: 1,
            }
            this.$store.commit('addToCart', { ...item })
        },
    },
}
</script>

<style lang="scss" scoped>
button {
    width: 100%;
    font-size: 1.25rem;

    @media (min-width: $grid-sm) {
        width: auto;
        font-size: 2rem;
    }

    svg {
        width: 2rem;
        height: auto;
        fill: $white;
    }
}
</style>
