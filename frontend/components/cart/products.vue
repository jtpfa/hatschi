<template>
    <div v-if="cartCountElements > 0" class="col-md-7">
        <b-card v-for="item in cart" :key="item.id" body-class="row" class="product">
            <div class="lazy-image col-6 d-flex justify-content-center align-items-center">
                <b-card-img-lazy alt="Image" class="mb-4" src="/img/sample.png" top />
                <spinner />
            </div>

            <div class="col-6">
                <h2 class="product-title font-weight-bold mb-3">{{ item.name }}</h2>
                <b-card-text class="product-description mb-2">
                    {{ $textCropper.cropText(item.description, 25) }}
                </b-card-text>
                <b-card-text class="product-price font-weight-bold text-primary big-noodle">
                    {{ $currencyConverter.insertFractionForEuroConversation(item.price) | currency }}
                </b-card-text>
            </div>
            <b-card-body>
                <div class="d-flex justify-content-end">
                    <b-button class="mr-2 mr-md-3" variant="outline-light" @click="removeAllFromCart(item)">
                        <icon-trash class="trash-icon" />
                    </b-button>
                    <b-input-group class="w-auto">
                        <b-input-group-prepend>
                            <b-button
                                variant="outline-light"
                                @click="item.quantity > 1 ? removeOneFromCart(item) : null"
                            >
                                -
                            </b-button>
                        </b-input-group-prepend>

                        <b-input-group-text class="bg-light">
                            <strong>{{ item.quantity }}</strong>
                        </b-input-group-text>

                        <b-input-group-append>
                            <b-button variant="outline-light" @click="addOneToCart(item)">+</b-button>
                        </b-input-group-append>
                    </b-input-group>
                </div>
            </b-card-body>
        </b-card>
    </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import IconTrash from '~/components/icons/trash'
import Spinner from '~/components/layout/spinner'

export default {
    name: 'CartProducts',
    components: { IconTrash, Spinner },
    computed: {
        ...mapState(['cart']),
        ...mapGetters(['cartCountElements', 'cartTotal']),
    },
    methods: {
        addOneToCart(item) {
            this.$store.commit('addOneToCart', item)
        },
        removeOneFromCart(item) {
            this.$store.commit('removeOneFromCart', item)
        },
        removeAllFromCart(item) {
            this.$store.commit('removeAllFromCart', item)
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

    .lazy-image {
        position: relative;
        flex-shrink: 0;
        width: 100%;
        min-height: 8rem;
        max-height: 100%;
        overflow: hidden;

        img {
            display: block;
            max-width: 90%;
            margin: 0 auto;

            @media (min-width: $grid-sm) {
                max-width: 80%;
            }

            @media (min-width: $grid-xl) {
                max-width: 55%;
            }
        }
    }

    .product-title {
        font-size: 1rem;
        font-family: $font-family-base;
        color: $secondary;

        @media (min-width: $grid-md) {
            font-size: 1.25rem;
        }
    }

    .product-description {
        color: $secondary;
    }

    .product-price {
        font-size: 1.2rem;

        @media (min-width: $grid-sm) {
            font-size: 1.5rem;
        }
    }

    .trash-icon {
        width: 1.5rem;
        fill: $secondary;
    }
}

// Override bootstrap default
.input-group-text {
    border: 1px solid #e9ecef !important;
}

.btn-outline-light {
    color: $black !important;
}
</style>
