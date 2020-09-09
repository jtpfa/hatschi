<template>
    <b-card title="Zusammenfassung">
        <cart-price-overview />

        <div class="d-flex justify-content-center align-items-center flex-column">
            <b-alert class="w-100" :show="error.length > 0" variant="danger" v-html="error" />

            <slot />
        </div>
    </b-card>
</template>

<script>
/**
 * @component CartSummary
 * @desc Summary of the cart (includes {link component:PriceOverview PriceOverview}, an error message and a custom slot)
 * @author Jonas Pfannkuche
 */

import CartPriceOverview from '~/components/shop/cart/priceOverview'

export default {
    name: 'CartSummary',
    components: { CartPriceOverview },
    props: {
        /**
         * @vprop {String} error - General error message
         */
        error: {
            type: String,
            default: '',
        },
    },
}
</script>

<style scoped></style>
