<template>
    <div class="row">
        <div class="col-12 col-md-5 d-flex justify-content-center align-items-center mb-5 mb-md-0">
            <div class="lazy-image d-flex justify-content-center align-items-center">
                <b-img-lazy alt="Image" src="/img/sample.png" />
                <spinner />
            </div>
        </div>

        <div class="col-12 col-md-7">
            <h1 class="text-primary text-center text-md-left oblique mb-0">Acer Predator-X B3 Serie</h1>
            <span class="delivery-time d-block text-center text-md-left text-muted">Lieferbar in 3-4 Werktagen</span>

            <p class="mt-5">
                Highlights: 16:9 Gaming Monitor mit einer Auflösung von 1920x1080, DisplayHDR 400, Fast LC 1ms (G2G),
                G-Sync Kompatibel
            </p>
            <p class="mt-5">
                G-Sync Kompatibel sind Freesync (Adaptive Sync) Monitore, die sich vergleichbar wie Gsync Monitore
                verhalten und somit ruckelfreie, flüssige Spielesessions ermöglichen.
            </p>

            <div class="d-flex justify-content-between align-items-end flex-column flex-sm-row mt-5">
                <div class="price-information mb-2 mb-sm-0">
                    <span class="price big-noodle oblique text-dark">834.39 €</span>
                    <span class="text-muted">inkl. MwSt.</span>
                    <span class="text-muted">zzgl. Versandkosten</span>
                </div>

                <b-button class="d-flex justify-content-center align-items-center px-5 py-1" variant="primary">
                    <icon-shopping-cart class="mr-3" />
                    In den Warenkorb
                </b-button>
            </div>
        </div>
    </div>
</template>

<script>
import IconShoppingCart from '~/components/icons/shoppingCart'
import Spinner from '~/components/layout/spinner'

export default {
    name: 'ProductShortDescription',
    components: { Spinner, IconShoppingCart },
    props: {
        product: {
            type: Object,
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
    min-height: 8rem;
    max-height: 100%;
    overflow: hidden;

    img {
        width: 50%;
    }
}
.delivery-time {
    margin-top: -0.5rem;
}

.price-information {
    span {
        display: block;
        line-height: 1;
    }

    .price {
        font-size: 2.5rem;
    }

    span:not(.price) {
        font-size: 0.8rem;
    }
}

button {
    width: 100%;
    font-size: 1.25rem;

    @media (min-width: $grid-sm) {
        width: auto;
        font-size: 2rem;
    }

    svg {
        width: 2rem;
        height: auto;
        fill: $white;
    }
}
</style>
