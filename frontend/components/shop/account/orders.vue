<template>
    <div>
        <b-alert v-if="fetchErrorMsg.length > 0" :show="true" variant="warning">{{ fetchErrorMsg }}</b-alert>

        <b-alert v-else-if="orders.length === 0" :show="true" variant="info">Keine Bestellungen getätigt.</b-alert>

        <b-card v-for="item in orders" v-else :key="item.id">
            <template v-slot:header class="d-flex flex-column flex-md-row">
                <h2>Blubdiblub</h2>
            </template>
            {{ item }}
        </b-card>
    </div>
</template>

<script>
export default {
    name: 'AccountOrders',
    async fetch() {
        try {
            this.orders = await this.$api.getAllOrdersOfCustomer(this.$auth.getToken('keycloak'))
        } catch (err) {
            this.fetchErrorMsg =
                err.message || 'Leider gab es ein Problem beim Laden der Daten. Bitte später erneut versuchen.'
        }
    },
    data() {
        return {
            orders: [],
            fetchErrorMsg: '',
        }
    },
}
</script>

<style scoped></style>
