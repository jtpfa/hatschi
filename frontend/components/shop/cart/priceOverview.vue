<template>
    <b-table-simple borderless class="mt-4 mb-0" small striped>
        <caption class="small">
            {{ standardShipping ? 'Lieferbar in 2-4 Werktagen' : 'Lieferbar in 1-2 Werktagen' }}
        </caption>
        <colgroup>
            <col />
            <col />
        </colgroup>
        <colgroup><col /></colgroup>
        <b-thead>
            <b-tr>
                <b-th>Name</b-th>
                <b-th class="text-center">Menge</b-th>
                <b-th class="text-right">Preis</b-th>
            </b-tr>
        </b-thead>
        <b-tbody>
            <b-tr v-for="item in cart" :key="item.id">
                <b-td>{{ item.name }}</b-td>
                <b-td class="text-center">{{ item.quantity }}</b-td>
                <b-td class="text-right">
                    {{ $currencyConverter.convertCentsToEuro(item.price * item.quantity) }}
                </b-td>
            </b-tr>
        </b-tbody>
        <b-tfoot class="border-top">
            <b-tr>
                <b-td colspan="2">
                    <strong>Gesamtsumme</strong>
                    <span class="text-muted small">(inkl. MwSt)</span>
                </b-td>
                <b-td class="text-right">
                    {{ $currencyConverter.convertCentsToEuro(cartTotal) }}
                </b-td>
            </b-tr>
        </b-tfoot>
    </b-table-simple>
</template>

<script>
export default {
    name: 'CartPriceOverview',
    computed: {
        cartTotal() {
            return this.$store.state.shoppingcart.cartTotal
        },
        cart() {
            return this.$store.state.shoppingcart.cart
        },
        standardShipping() {
            return this.$store.state.order.shippingMethod.description?.toLocaleLowerCase() === 'standardversand'
        },
    },
}
</script>

<style scoped></style>
