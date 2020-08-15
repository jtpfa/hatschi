<template>
    <b-card sub-title="Gutscheine können im Zahlungsschritt hinzugefügt werden" title="Zusammenfassung">
        <b-table-simple borderless class="my-4" small striped>
            <caption class="small">
                Lieferbar in 3-4 Werktagen
            </caption>
            <colgroup>
                <col />
                <col />
            </colgroup>
            <colgroup><col /></colgroup>
            <b-thead>
                <b-tr>
                    <b-th>Name</b-th>
                    <b-th class="text-center">Menge</b-th>
                    <b-th class="text-right">Preis</b-th>
                </b-tr>
            </b-thead>
            <b-tbody>
                <b-tr v-for="item in cart" :key="item.id">
                    <b-td>{{ item.name }}</b-td>
                    <b-td class="text-center">{{ item.quantity }}</b-td>
                    <b-td class="text-right">
                        {{ $currencyConverter.insertFractionForEuroConversion(item.price * item.quantity) | currency }}
                    </b-td>
                </b-tr>
            </b-tbody>
            <b-tfoot class="border-top">
                <b-tr>
                    <b-td colspan="2">
                        <strong>Gesamtsumme</strong>
                        <span class="text-muted small">(inkl. MwSt)</span>
                    </b-td>
                    <b-td class="text-right">
                        {{ $currencyConverter.insertFractionForEuroConversion(cartTotal) | currency }}
                    </b-td>
                </b-tr>
            </b-tfoot>
        </b-table-simple>
        <div class="d-flex justify-content-center align-items-center flex-column">
            <b-alert :show="error.length > 0" variant="danger">{{ error }}</b-alert>
            <b-button class="my-3 w-100" size="lg" variant="primary" @click="onSubmit">
                {{ cartChanged ? 'Warenkorb geprüft? Ab zur Kasse' : 'Zur Kasse gehen' }}
            </b-button>
            <b-button class="w-100" to="/" variant="white">Weiter einkaufen</b-button>
        </div>
    </b-card>
</template>

<script>
import { mapGetters, mapState } from 'vuex'

export default {
    name: 'CartPriceOverview',
    data() {
        return {
            error: '',
            cartChanged: false,
        }
    },
    computed: {
        ...mapGetters(['cartTotal', 'changedStockOfCartElements']),
        ...mapState(['cart']),
    },
    methods: {
        onSubmit() {
            this.cartChanged = false
            new Promise((resolve, reject) => {
                this.cart.forEach(async (item, index, array) => {
                    try {
                        const product = await this.$api.getProduct(item.id)
                        this.$store.commit('updateCart', product)

                        if (this.changedStockOfCartElements) {
                            this.cartChanged = true
                            this.$root.$bvToast.toast(
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
                            this.$store.commit('resetStockElementsChangeListener')
                        }

                        if (index === array.length - 1) {
                            resolve()
                        }
                    } catch (err) {
                        this.error = 'Bitte Seite neu laden. Es gibt ein Problem mit mind. einem der Produkte.'
                        reject()
                    }
                })
            }).then(() => {
                if (!this.cartChanged) {
                    this.$router.push('/bestellung')
                }
            })
        },
    },
}
</script>

<style scoped></style>
