<template>
    <div>
        <p class="quantity">
            <button class="update-num" @click="quantity > 0 ? quantity-- : (quantity = 0)">-</button>
            <input v-model="quantity" type="number" />
            <button class="update-num" @click="quantity++">+</button>
        </p>
        <button class="button purchase" @click="addToCart">Add to Cart</button>
    </div>
</template>

<script>
export default {
    name: 'AddToCart',
    props: {
        name: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            quantity: 1,
        }
    },
    methods: {
        addToCart() {
            let item = this.product
            item = {
                ...item,
                id: this.name,
                quantity: this.quantity,
            }
            this.$store.commit('addToCart', { ...item })
        },
    },
}
</script>

<style scoped></style>
