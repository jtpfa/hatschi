<template>
    <div class="row">
        <div class="col-md-7 d-flex justify-content-between align-items-center">
            <b-button v-if="step === 1" size="lg" to="/warenkorb" variant="light">
                &larr; Zurück
            </b-button>
            <b-button v-else-if="step > 1" size="lg" variant="light" @click="$emit('back')">
                &larr; Zurück
            </b-button>
            <b-button
                class="d-flex justify-content-center align-items-center my-3"
                :disabled="loading"
                size="lg"
                type="submit"
                variant="primary"
            >
                <b-spinner v-if="loading" small></b-spinner>
                {{ step === 3 ? 'Jetzt kaufen' : 'Weiter' }} &rarr;
            </b-button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'OrderPagination',
    props: {
        loading: {
            type: Boolean,
            default: false,
        },
    },
    computed: {
        step: {
            get() {
                return this.$store.state.order.step
            },
            set(step) {
                this.$store.commit('order/updateOrderInformation', { key: 'step', data: step })
            },
        },
    },
}
</script>

<style scoped></style>
