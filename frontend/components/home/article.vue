<template>
    <b-card class="article">
        <b-card-img-lazy alt="Image" :src="imageSrc" class="mb-4" top />
        <h2 class="article-title font-weight-bold mb-4">{{ name }}</h2>
        <b-card-text class="article-description mb-4">
            {{ cropText }}
        </b-card-text>
        <b-card-text class="article-price font-weight-bold text-primary big-noodle">{{ price }} €</b-card-text>
    </b-card>
</template>

<script>
export default {
    name: 'homeArticle',
    props: {
        name: {
            type: String,
            required: true,
        },
        text: {
            type: String,
            required: true,
        },
        price: {
            type: Number,
            required: true,
        },
        imageSrc: {
            type: String,
            required: true,
        },
    },
    computed: {
        // Get 50 characters of the description text and append '...'
        cropText() {
            let description = this.text.substring(0, 49)

            // Add a space if the cropped text doesn't end with one
            if (description.slice(-1) !== ' ') {
                description += ' '
            }

            description += '...'

            return description
        },
    },
}
</script>

<style lang="scss" scoped>
.article {
    border-radius: 0.75rem;
    border: none;
    box-shadow: 0 0 7px 1px rgba(0, 0, 11, 0.16);
    transition: all 0.3s ease-in-out;

    &:hover {
        box-shadow: 0 0 7px 1px $primary;
    }

    img {
        display: block;
        max-width: 35%;
        margin: 0 auto;

        @media (min-width: $grid-sm) {
            max-width: 45%;
        }

        @media (min-width: $grid-xl) {
            max-width: 55%;
        }
    }

    .article-title {
        font-size: 1.5rem;
        font-family: $font-family-base;
        color: $secondary;

        @media (min-width: $grid-md) {
            font-size: 1.75rem;
        }
    }

    .article-description {
        color: $secondary;
    }

    .article-price {
        font-size: 2rem;

        @media (min-width: $grid-md) {
            font-size: 2.5rem;
        }
    }
}
</style>
