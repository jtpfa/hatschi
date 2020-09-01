<template>
    <div class="big-noodle mt-3 mt-sm-5 mb-4 mb-sm-5">
        <div class="info-cards">
            <span class="product-name d-block bg-white text-primary text-center px-3 py-1 mb-3">{{ name }}</span>
            <div class="features d-block text-center">
                <span class="d-inline-block text-white feature-one px-3 py-1">{{ featureOne }}</span>
                <span class="d-inline-block text-white feature-two px-3 py-1">{{ featureTwo }}</span>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'HomeSlideInfoCards',
    props: {
        name: {
            type: String,
            required: true,
        },
        featureOne: {
            type: String,
            required: true,
        },
        featureTwo: {
            type: String,
            required: true,
        },
    },
}
</script>

<style lang="scss" scoped>
.product-name,
.features {
    width: 75%;
    margin: auto;

    @media (min-width: $grid-sm) {
        width: 100%;
    }
}

.product-name,
span[class*='feature-'] {
    border: 0.2rem solid $white;
    font-size: 1.5rem;

    @media (min-width: $grid-sm) {
        font-size: 2.5rem;
    }
}
</style>
