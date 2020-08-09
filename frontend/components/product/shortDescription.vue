<template>
    <div class="row">
        <div class="col-12 col-md-5 d-flex justify-content-center align-items-center mb-5 mb-md-0">
            <div class="lazy-image d-flex justify-content-center align-items-center">
                <b-img-lazy alt="Image" src="/img/sample.png" />
                <spinner />
            </div>
        </div>

        <div class="col-12 col-md-7">
            <h1 class="text-primary text-center text-md-left oblique mb-0">{{ product.name }}</h1>
            <span class="delivery-time d-block text-center text-md-left text-muted">{{ deliveryInformation }}</span>

            <p class="mt-5">
                {{ product.description }}
            </p>

            <div class="d-flex justify-content-between align-items-end flex-column flex-sm-row mt-5">
                <div class="price-information mb-2 mb-sm-0">
                    <span class="price big-noodle oblique text-dark">
                        {{ $currencyConverter.insertFractionForEuroConversion(product.price) | currency }}
                    </span>
                    <span class="text-muted">inkl. MwSt.</span>
                    <span class="text-muted">zzgl. Versandkosten</span>
                </div>

                <add-to-cart :orderable="product.stock > 0" :product="product" />
            </div>
        </div>
    </div>
</template>

<script>
import AddToCart from '~/components/cart/addToCart'
import Spinner from '~/components/layout/spinner'

export default {
    name: 'ProductShortDescription',
    components: { AddToCart, Spinner },
    props: {
        product: {
            type: Object,
            required: true,
        },
    },
    computed: {
        deliveryInformation() {
            return this.product.stock === 0 ? 'Nicht auf Lager' : 'Lieferbar in 3-4 Werktagen'
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
</style>
