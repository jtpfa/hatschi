<template>
    <b-jumbotron
        bg-variant="success"
        class="my-5"
        header="Einkauf erfolgreich getätigt."
        :header-level="1"
        text-variant="white"
    >
        <p>Vielen Dank für deinen Einkauf. Wir kümmern uns nun um deine Bestellung.</p>
    </b-jumbotron>
</template>

<script>
/**
 * @component OrderConfirmation
 * @desc Message to show successful order
 * @author Jonas Pfannkuche
 */

export default {
    name: 'OrderConfirmation',
}
</script>

<style scoped></style>
