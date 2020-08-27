<template>
    <div>
        <b-alert :show="fetchErrorMsg.length > 0" variant="warning">{{ fetchErrorMsg }}</b-alert>

        <b-card v-for="item in orders" :key="item.id">
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
