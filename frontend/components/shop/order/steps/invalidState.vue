<template>
    <b-jumbotron bg-variant="light" class="my-5" header="Upps... Diesen Schritt gibt es nicht" :header-level="4">
        <p>
            Leider gab es einen Fehler im Bestellprozess. Die bereits eingegeben Daten gehen allerdings
            <strong>nicht</strong>
            verloren!
        </p>
        <b-button @click="goToInitialStep">Zum ersten Schritt</b-button>
    </b-jumbotron>
</template>

<script>
/**
 * @component OrderInvalidState
 * @desc Invalid state in order process (e. g. step isn't between 1 and 3)
 * @author Jonas Pfannkuche
 */

export default {
    name: 'OrderInvalidState',
    computed: {
        /**
         * @computed {Number} step - Current step of order process
         */
        step: {
            get() {
                return this.$store.state.order.step
            },
            set(step) {
                this.$store.commit('order/updateOrderInformation', { key: 'step', data: step })
            },
        },
    },
    methods: {
        /**
         * @method goToInitialStep
         * @desc Navigates to first order step
         */
        goToInitialStep() {
            this.step = 1
            this.$router.push({ path: '/bestellung', query: { step: this.step } })
        },
    },
}
</script>

<style scoped></style>
