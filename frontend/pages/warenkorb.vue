<template>
    <div>
        <add-to-cart name="produkt1" />
        <add-to-cart name="produkt2" />
    </div>
</template>

<script>
import AddToCart from '~/components/cart/addToCart'

export default {
    components: { AddToCart },
}
</script>

<style scoped></style>
