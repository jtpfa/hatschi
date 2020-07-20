<template>
    <div>
        <add-to-cart name="artikel1" />
        <add-to-cart name="artikel2" />
    </div>
</template>

<script>
import AddToCart from '~/components/cart/addToCart'

export default {
    components: { AddToCart },
}
</script>

<style scoped></style>
