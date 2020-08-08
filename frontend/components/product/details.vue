<template>
    <div>
        <p class="h5">Produktdetails</p>

        <p>
            {{ product.details }}
        </p>
    </div>
</template>

<script>
export default {
    name: 'ProductDetails',
    props: {
        product: {
            type: Object,
            required: true,
        },
    },
}
</script>

<style scoped></style>
