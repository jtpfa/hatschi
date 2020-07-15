<template>
    <div class="overflow-container">
        <div class="shape">
            <div class="row h-100 skew-10-deg">
                <slot />
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'homeSlideBackgroundShape',
}
</script>

<style lang="scss" scoped>
.overflow-container {
    height: 100%;
    overflow: hidden;
}

.shape {
    background-color: $primary;
    height: 100%;
    width: 100%;
    border-radius: 0.75rem;

    @media (min-width: $grid-sm) {
        border-radius: 0.75rem 0 0 5.625rem / 0.75rem 0 0 5rem;
        transform: skew(10deg) translateX(1.6rem);
    }

    @media (min-width: $grid-lg) {
        transform: skew(10deg) translateX(2rem);
    }

    @media (min-width: $grid-xl) {
        transform: skew(10deg) translateX(2.5rem);
    }
}

.skew-10-deg {
    @media (min-width: $grid-sm) {
        transform: skew(-10deg);
    }
}
</style>
