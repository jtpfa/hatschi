<template>
    <b-container>
        <h1 class="my-5">Kontakt</h1>
        <b-button href="mailto:test@mail.de" variant="primary">E-Mail schreiben</b-button>
    </b-container>
</template>

<script>
export default {
    head() {
        return {
            title: `PC Masterrace – ${this.$route.name.replace(/^\w/, c => c.toUpperCase())}`,
        }
    },
}
</script>

<style scoped></style>
