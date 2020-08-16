<template>
    <h1 v-if="step === 0">Lieferungsdaten</h1>
    <h1 v-else-if="step === 1">Zahlungsdaten & Vouchers</h1>
    <h1 v-else-if="step === 2">Übersicht</h1>
    <h1 v-else-if="step === 3">Bestellbestätigung</h1>
</template>

<script>
export default {
    name: 'OrderHeadline',
    computed: {
        step: {
            get() {
                return this.$store.state.order.step
            },
        },
    },
}
</script>

<style scoped></style>
