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
        stockOfElementChanged() {
            return this.$store.state.shoppingcart.stockOfElementChanged
        },
    },
    methods: {
        async onSubmit() {
            this.cartChanged = false

            const results = this.cart.map(item => {
                return new Promise((resolve, reject) => {
                    try {
                        this.$api
                            .getProduct(item.id)
                            .then(product => {
                                this.$store.commit('shoppingcart/updateCart', product)

                                if (this.stockOfElementChanged) {
                                    this.cartChanged = true

                                    if (product.stock === 0) {
                                        this.$store.commit('shoppingcart/removeAllFromCart', item)
                                        this.generateToastMessage(
                                            'Es gab eine Änderung des Warenkorbs',
                                            `Wir mussten leider "${item.name}" aus deinem Warenkorb entfernen, da dieser Artikel nicht mehr verfügbar ist.`,
                                            'danger'
                                        )
                                    } else {
                                        this.generateToastMessage(
                                            'Es gab eine Änderung beim Lagerbestand',
                                            `Wir mussten leider bei "${item.name}" deine gewünschte Menge auf unseren neuen Lagerstand aktualisieren. Bitte den Warenkorb überprüfen und erneut absenden.`,
                                            'warning'
                                        )
                                    }
                                }
                            })
                            .then(() => resolve())
                    } catch (err) {
                        if (err.message.toLocaleLowerCase().includes('kein artikel')) {
                            this.cartChanged = true
                            this.$store.commit('shoppingcart/removeAllFromCart', item)
                            this.generateToastMessage(
                                'Es gab eine Änderung des Warenkorbs',
                                `Wir mussten leider "${item.name}" aus deinem Warenkorb entfernen, da dieser Artikel nicht mehr verfügbar ist.`,
                                'danger'
                            )
                        } else {
                            this.error =
                                err.message ||
                                'Bitte Seite neu laden. Es gibt ein Problem mit mind. einem der Produkte.'
                            reject()
                        }
                    }
                })
            })

            await Promise.allSettled(results).then(() => {
                if (!this.cartChanged) {
                    this.$router.push({ path: '/bestellung', query: { step: 1 } })
                }
            })
        },
        generateToastMessage(title, description, variant) {
            this.$bvToast.toast(description, {
                title,
                noAutoHide: true,
                appendToast: true,
                isStatus: true,
                solid: true,
                variant,
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
