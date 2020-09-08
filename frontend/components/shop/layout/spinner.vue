<template>
    <div class="orbit-spinner" :style="{ width: size + 'rem', height: size + 'rem' }">
        <div
            v-for="i in 3"
            :key="i"
            class="orbit"
            :class="{ 'border-primary': color === 'primary', 'border-white': color === 'white' }"
        ></div>
    </div>
</template>

<script>
/**
 * @component Spinner
 * @desc Spinner to show loading state (especially, image loading and data fetching)
 * @author Jonas Pfannkuche
 */

export default {
    name: 'Spinner',
    props: {
        /**
         * @vprop {String} color - Bootstrap theme color
         */
        color: {
            type: String,
            default: 'primary',
        },
        /**
         * @vprop {Number} size - Size of spinner in rem
         */
        size: {
            type: Number,
            default: 5,
        },
    },
}
</script>

<style lang="scss" scoped>
// Design idea from https://epic-spinners.epicmax.co/
.orbit-spinner {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    margin: auto;

    .orbit {
        position: absolute;
        width: 100%;
        height: 100%;
        border-radius: 50%;

        &.border-white {
            border-color: $white;
        }

        &:nth-child(1) {
            left: 0;
            top: 0;
            animation: orbit-spinner-orbit-one-animation 1200ms linear infinite;
            border-bottom: 3px solid;
        }

        &:nth-child(2) {
            right: 0;
            top: 0;
            animation: orbit-spinner-orbit-two-animation 1200ms linear infinite;
            border-right: 3px solid;
        }

        &:nth-child(3) {
            right: 0;
            bottom: 0;
            animation: orbit-spinner-orbit-three-animation 1200ms linear infinite;
            border-top: 3px solid;
        }
    }
}

@keyframes orbit-spinner-orbit-one-animation {
    0% {
        transform: rotateX(35deg) rotateY(-45deg) rotateZ(0deg);
    }
    100% {
        transform: rotateX(35deg) rotateY(-45deg) rotateZ(360deg);
    }
}

@keyframes orbit-spinner-orbit-two-animation {
    0% {
        transform: rotateX(50deg) rotateY(10deg) rotateZ(0deg);
    }
    100% {
        transform: rotateX(50deg) rotateY(10deg) rotateZ(360deg);
    }
}

@keyframes orbit-spinner-orbit-three-animation {
    0% {
        transform: rotateX(35deg) rotateY(55deg) rotateZ(0deg);
    }
    100% {
        transform: rotateX(35deg) rotateY(55deg) rotateZ(360deg);
    }
}
</style>
