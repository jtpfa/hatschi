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
        product: {
            type: Object,
            required: true,
        },
        orderable: {
            type: Boolean,
            required: true,
        },
    },
    methods: {
        addToCart() {
            const item = {
                ...this.product,
                quantity: 1,
            }
            this.$store.commit('shoppingcart/addToCart', { ...item })

            this.$root.$bvToast.toast(`"${this.product.name}" liegt nun im Warenkorb`, {
                title: 'Artikel wurde dem Warenkorb hinzugefügt',
                autoHideDelay: 3000,
                appendToast: true,
                isStatus: true,
                solid: true,
                variant: 'success',
            })
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
