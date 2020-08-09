<template>
    <b-card sub-title="Gutscheine können im Zahlungsschritt hinzugefügt werden" title="Zusammenfassung">
        <b-table-simple borderless class="my-4" small striped>
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
            <b-button class="my-3 w-100" variant="primary">Zur Kasse gehen</b-button>
            <b-button class="w-100" to="/" variant="white">Weiter einkaufen</b-button>
        </div>
    </b-card>
</template>

<script>
import { mapGetters, mapState } from 'vuex'

export default {
    name: 'CartPriceOverview',
    computed: {
        ...mapGetters(['cartTotal']),
        ...mapState(['cart']),
    },
}
</script>

<style scoped></style>
