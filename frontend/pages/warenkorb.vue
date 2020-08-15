<template>
    <client-only>
        <b-container>
            <template v-if="cartCountElements > 0">
                <div class="row my-5">
                    <div class="col-md-7 mb-5 mb-md-0">
                        <cart-products />
                    </div>
                    <div class="col-md-5">
                        <cart-summary :error="error">
                            <b-button class="my-3 w-100" size="lg" variant="primary" @click="onSubmit">
                                {{ cartChanged ? 'Warenkorb geprüft? Ab zur Kasse' : 'Zur Kasse gehen' }}
                            </b-button>
                            <b-button class="w-100" to="/" variant="white">Weiter einkaufen</b-button>
                        </cart-summary>
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
import CartProducts from '~/components/cart/products'
import CartSummary from '~/components/cart/summary'

export default {
    components: { CartSummary, CartProducts },
    data() {
        return {
            error: '',
            cartChanged: false,
        }
    },
    computed: {
        ...mapGetters({ cartCountElements: 'shoppingcart/cartCountElements' }),
        cart() {
            return this.$store.state.shoppingcart.cart
        },
        stockOfCartElementsChanged() {
            return this.$store.state.shoppingcart.stockOfCartElementsChanged
        },
    },
    methods: {
        onSubmit() {
            this.cartChanged = false
            new Promise((resolve, reject) => {
                this.cart.forEach(async (item, index, array) => {
                    try {
                        const product = await this.$api.getProduct(item.id)
                        this.$store.commit('shoppingcart/updateCart', product)

                        if (this.stockOfCartElementsChanged) {
                            this.cartChanged = true
                            this.$bvToast.toast(
                                `Wir mussten leider bei "${item.name}" deine gewünschte Menge auf unseren neuen Lagerstand aktualisieren. Bitte den Warenkorb überprüfen und erneut absenden.`,
                                {
                                    title: 'Es gab eine Änderung beim Lagerbestand',
                                    noAutoHide: true,
                                    appendToast: true,
                                    isStatus: true,
                                    solid: true,
                                    variant: 'warning',
                                }
                            )
                            this.$store.commit('shoppingcart/resetStockElementsChangeListener')
                        }

                        if (index === array.length - 1) {
                            resolve()
                        }
                    } catch (err) {
                        this.error = 'Bitte Seite neu laden. Es gibt ein Problem mit mind. einem der Produkte.'
                        reject()
                    }
                })
            })
                .then(() => {
                    if (!this.cartChanged) {
                        this.$router.push('/bestellung')
                    }
                })
                .catch(() => {
                    this.error = 'Leider gab es ein Problem. Bitte versuch es später erneut.'
                })
        },
    },
    head() {
        return {
            title: `PC Masterrace – ${this.$route.name.replace(/^\w/, c => c.toUpperCase())}`,
        }
    },
}
</script>

<style scoped></style>
