<template>
    <div class="col-12 col-md-4 d-flex flex-column justify-content-between order-md-0">
        <div>
            <h2 class="text-primary">{{ headline }}</h2>
            <p>
                {{ text }}
            </p>
        </div>
    </div>
</template>

<script>
/**
 * @component HomeSlideDescription
 * @desc Title and description text of a slide
 * @author Jonas Pfannkuche
 */

export default {
    name: 'HomeSlideDescription',
    props: {
        /**
         * @vprop {String} headline - Headline of the slide
         */
        headline: {
            type: String,
            required: true,
        },
        /**
         * @vprop {String} text - Description of the slide
         */
        text: {
            type: String,
            required: true,
        },
    },
}
</script>

<style lang="scss" scoped>
button {
    font-size: 1.25rem;
}
</style>
