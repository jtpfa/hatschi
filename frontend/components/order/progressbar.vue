<template>
    <div>
        <b-progress :max="6" :value="progressBarValue" />
        <div class="position-absolute d-flex justify-content-around align-items-center w-100 my-3 steps">
            <b-avatar
                v-for="i in 3"
                :key="i"
                badge-offset="-0.2rem"
                badge-variant="success"
                :button="step >= i && step < 3"
                class="big-noodle"
                size="3.5rem"
                :variant="step + 1 >= i ? 'primary' : 'outline-primary'"
                @click="step = i - 1"
            >
                {{ i }}
                <template v-if="step >= i" v-slot:badge>&#10003;</template>
            </b-avatar>
        </div>
    </div>
</template>

<script>
export default {
    name: 'OrderProgressbar',
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
            if (this.step > 3) {
                return 0
            }

            if (this.step === 0) {
                return 1
            }

            return this.step * 2 + 1
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
}
</style>
