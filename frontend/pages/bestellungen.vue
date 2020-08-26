<template>
    <b-container class="my-5">
        <h1>Bestellungen</h1>

        <customer-related-orders />
    </b-container>
</template>

<script>
import CustomerRelatedOrders from '~/components/shop/account/orders'

export default {
    name: 'Bestellungen',
    components: { CustomerRelatedOrders },
    middleware: ['auth'],
    head() {
        return {
            title: `${this.$route.name.replace(/^\w/, c => c.toUpperCase())} – PC Masterrace`,
        }
    },
}
</script>

<style scoped></style>
