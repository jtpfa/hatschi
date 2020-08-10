<template>
    <client-only>
        <b-container>
            <template v-if="cartCountElements > 0">
                <div class="row my-5">
                    <div class="col-md-7 mb-5 mb-md-0">
                        <cart-products />
                    </div>
                    <div class="col-md-5">
                        <cart-price-overview />
                    </div>
                </div>
            </template>
            <template v-else>
                <b-jumbotron
                    bg-variant="light"
                    class="my-5"
                    header="Warenkorb ist leer"
                    :header-level="4"
                ></b-jumbotron>
            </template>
        </b-container>
    </client-only>
</template>

<script>
import { mapGetters } from 'vuex'
import CartPriceOverview from '~/components/cart/priceOverview'
import CartProducts from '~/components/cart/products'

export default {
    components: { CartPriceOverview, CartProducts },
    head() {
        return {
            title: `PC Masterrace – ${this.$route.name.replace(/^\w/, c => c.toUpperCase())}`,
        }
    },
    computed: {
        ...mapGetters(['cartCountElements']),
    },
}
</script>

<style scoped></style>
