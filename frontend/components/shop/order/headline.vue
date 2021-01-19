<template>
    <h1 v-if="step === 1">Lieferungsdaten</h1>
    <h1 v-else-if="step === 2">Zahlungsdaten & Vouchers</h1>
    <h1 v-else-if="step === 3">Übersicht</h1>
</template>

<script>
/**
 * @component OrderHeadline
 * @desc Headline of the current order step
 * @author Jonas Pfannkuche
 */

export default {
    name: 'OrderHeadline',
    computed: {
        /**
         * @computed {Number} step - Current step of order process
         */
        step: {
            get() {
                return this.$store.state.order.step
            },
        },
    },
}
</script>

<style scoped></style>
