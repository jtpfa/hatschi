<template>
    <b-button
        class="d-flex justify-content-center align-items-center px-4 py-2 py-sm-1"
        :disabled="!orderable"
        variant="primary"
        @click="addToCart"
    >
        <icon-shopping-cart class="mr-3" />
        {{ orderable ? 'In den Warenkorb' : 'Nicht mehr verfügbar' }}
    </b-button>
</template>

<script>
/**
 * @component AddToCart
 * @desc Button to add a product to the cart
 * @author Jonas Pfannkuche
 */

import IconShoppingCart from '~/components/general/icons/shoppingCart'

export default {
    name: 'AddToCart',
    components: { IconShoppingCart },
    props: {
        /**
         * @vprop {Object} product - Product which could be added to the cart
         */
        product: {
            type: Object,
            required: true,
        },
        /**
         * @vprop {Boolean} orderable - Product is orderable or not
         */
        orderable: {
            type: Boolean,
            required: true,
        },
    },
    methods: {
        /**
         * @method addToCart
         * @desc Adds the product to the cart and shows a pop-up success message
         */
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
        font-size: 1.75rem;
    }

    svg {
        width: 2rem;
        height: auto;
        fill: $white;
    }
}
</style>
