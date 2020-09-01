<template>
    <div class="col-12 col-md-8 order-md-1 mb-5 mb-md-0">
        <home-slide-background-shape>
            <div class="col-12 col-sm-5 mt-2 mt-sm-0">
                <div class="lazy-image d-flex justify-content-center align-items-center">
                    <b-img-lazy :alt="name" src="~assets/img/stage-sample.png" :title="name" />
                    <spinner color="white" :size="6" />
                </div>
            </div>
            <div class="col-12 col-sm-6 d-flex flex-column justify-content-between">
                <home-slide-info-cards :feature-one="featureOne" :feature-two="featureTwo" :name="name" />
                <div
                    class="product-price font-weight-bold text-white big-noodle align-self-end mr-5 mr-sm-0 mb-3 mb-sm-0"
                >
                    {{ $currencyConverter.convertCentsToEuro(price) }}
                </div>
            </div>
        </home-slide-background-shape>
    </div>
</template>
<script>
import HomeSlideBackgroundShape from '~/components/shop/home/slide/backgroundShape'
import HomeSlideInfoCards from '~/components/shop/home/slide/infoCards'
import Spinner from '~/components/shop/layout/spinner'

export default {
    name: 'HomeSlideFacts',
    components: { Spinner, HomeSlideInfoCards, HomeSlideBackgroundShape },
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
        price: {
            type: [Number, String],
            required: true,
        },
    },
}
</script>

<style lang="scss" scoped>
.lazy-image {
    position: relative;
    flex-shrink: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;

    img {
        width: 40%;
        height: auto;

        @media (min-width: $grid-sm) {
            width: 80%;
        }

        @media (min-width: $grid-md) {
            width: 90%;
        }

        @media (min-width: $grid-lg) {
            width: 75%;
        }

        @media (min-width: $grid-xl) {
            width: 60%;
        }
    }
}

.product-price {
    font-size: 2.5rem;
    font-style: oblique;
}
</style>
