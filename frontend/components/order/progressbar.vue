<template>
    <div>
        <b-progress :max="6" :value="progressBarValue" />
        <div class="position-absolute d-flex justify-content-around align-items-center w-100 my-3 steps">
            <b-avatar
                v-for="i in 3"
                :key="i"
                badge-offset="-0.2rem"
                badge-variant="success"
                :button="step > i && step <= 3"
                class="big-noodle"
                size="3.5rem"
                :variant="step >= i || ready ? 'primary' : 'outline-primary'"
                @click="goToStep(i)"
            >
                {{ i }}
                <template v-if="step > i || ready" v-slot:badge>&#10003;</template>
            </b-avatar>
        </div>
    </div>
</template>

<script>
export default {
    name: 'OrderProgressbar',
    props: {
        ready: {
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
        progressBarValue() {
            if (!this.ready) {
                return this.step * 2 - 1
            }

            return 6
        },
    },
    methods: {
        goToStep(step) {
            this.step = step
            this.$router.push({ path: '/bestellung', query: { step: this.step } })
            this.$router.app.refresh()
        },
    },
}
</script>

<style lang="scss" scoped>
::v-deep {
    .progress {
        margin-bottom: 3.75rem;
    }

    .progress-bar {
        background: $primary;
    }
}

.steps {
    left: 0;
    top: -2.25rem;
}

.b-avatar {
    font-size: 1.75rem;
    box-shadow: none;
    transition: 0.2s all ease;
}

.badge-outline-primary {
    color: $primary;
    border: 1px solid $primary;
    background: $page-background;
}

button.b-avatar {
    background: $primary;
    box-shadow: 0 0 7px 1px rgba(0, 0, 11, 0.16);

    // @todo hover state
}
</style>
