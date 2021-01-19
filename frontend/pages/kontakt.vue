<template>
    <main class="container">
        <h1 class="my-5">Kontakt</h1>
        <b-button href="mailto:test@mail.de" variant="primary">E-Mail schreiben</b-button>
    </main>
</template>

<script>
/**
 * @component ContactPage
 * @desc Contact page
 * @author Jonas Pfannkuche
 */

export default {
    head() {
        return {
            title: `${this.$route.name.replace(/^\w/, c => c.toUpperCase())} – PC Masterrace`,
        }
    },
}
</script>

<style scoped></style>
