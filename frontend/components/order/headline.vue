<template>
    <h1 v-if="step === 0">Lieferungsdaten</h1>
    <h1 v-else-if="step === 1">Zahlungsart</h1>
    <h1 v-else-if="step === 2">Übersicht</h1>
</template>

<script>
export default {
    name: 'OrderHeadline',
    props: {
        step: {
            type: Number,
            required: true,
        },
    },
}
</script>

<style scoped></style>
