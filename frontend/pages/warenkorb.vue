<template>
    <client-only>
        <b-container>
            <cart-products />
        </b-container>
    </client-only>
</template>

<script>
import CartProducts from '~/components/cart/products'

export default {
    components: { CartProducts },
}
</script>

<style scoped></style>
