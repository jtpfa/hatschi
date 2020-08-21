<template>
    <div>
        <b-alert :show="fetchErrorMsg.length > 0" variant="warning">{{ fetchErrorMsg }}</b-alert>
        {{ orders }}
    </div>
</template>

<script>
export default {
    name: 'AllOrders',
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
