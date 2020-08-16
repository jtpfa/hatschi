<template>
    <b-card title="Zusammenfassung">
        <cart-price-overview />

        <div class="d-flex justify-content-center align-items-center flex-column">
            <b-alert class="w-100" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

            <slot />
        </div>
    </b-card>
</template>

<script>
import CartPriceOverview from '~/components/cart/priceOverview'

export default {
    name: 'CartSummary',
    components: { CartPriceOverview },
    props: {
        error: {
            type: String,
            default: '',
        },
    },
}
</script>

<style scoped></style>
