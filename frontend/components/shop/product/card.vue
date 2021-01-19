<template>
    <b-card body-class="d-flex flex-wrap align-content-between" class="product">
        <div class="w-100">
            <div class="lazy-image d-flex justify-content-center align-items-center">
                <b-card-img-lazy
                    :alt="product.name"
                    class="mb-4"
                    onerror="this.onerror=null;this.srcset='/img/logo-placeholder.svg';"
                    :src="$imageSrcSet.getImageUrl(product.id, 512)"
                    :srcset="$imageSrcSet.getSrcSet(product.id)"
                    :title="product.name"
                />
                <spinner />
            </div>

            <h2 class="product-title font-weight-bold mb-4">{{ product.name }}</h2>
            <b-card-text class="product-description mb-4">
                {{ $textCropper.cropText(product.description, 50) }}
            </b-card-text>
        </div>
        <div class="w-100">
            <b-card-text class="product-price mb-0 font-weight-bold text-primary big-noodle">
                {{ $currencyConverter.convertCentsToEuro(product.price) }}
            </b-card-text>
        </div>
        <b-link class="stretched-link" :title="`Detailseite von ${product.name}`" :to="`/produkte/${product.id}`" />
    </b-card>
</template>

<script>
/**
 * @component ProductCard
 * @desc Card to show basic information of a product with a link to the detailed page
 * @author Jonas Pfannkuche
 */

import Spinner from '~/components/shop/layout/spinner'

export default {
    name: 'ProductCard',
    components: { Spinner },
    props: {
        /**
         * @vprop {Object} product - Product with information that should be rendered
         */
        product: {
            type: Object,
            required: true,
        },
    },
}
</script>

<style lang="scss" scoped>
.product {
    border-radius: 0.75rem;
    border: none;
    box-shadow: 0 0 7px 1px rgba(0, 0, 11, 0.16);
    transition: all 0.3s ease-in-out;

    &:hover,
    &:focus {
        box-shadow: 0 0 7px 1px $primary;
        outline: none;
    }

    .lazy-image {
        position: relative;
        flex-shrink: 0;
        width: 100%;
        min-height: 8rem;
        max-height: 100%;
        overflow: hidden;

        img {
            display: block;
            max-width: 35%;

            @media (min-width: $grid-sm) {
                max-width: 45%;
            }

            @media (min-width: $grid-xl) {
                max-width: 55%;
            }
        }
    }

    .product-title {
        font-size: 1.5rem;
        font-family: $font-family-base;
        color: $secondary;

        @media (min-width: $grid-md) {
            font-size: 1.75rem;
        }
    }

    .product-description {
        color: $secondary;
    }

    .product-price {
        font-size: 2rem;

        @media (min-width: $grid-md) {
            font-size: 2.5rem;
        }
    }
}
</style>
