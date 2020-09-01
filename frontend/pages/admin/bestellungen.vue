<template>
    <main>
        <data-overview :dashboard="false" :fields="fields" type="order" />
    </main>
</template>

<script>
import DataOverview from '~/components/admin/data/overview'

export default {
    components: { DataOverview },
    layout: 'dashboard',
    data() {
        return {
            fields: [
                {
                    key: 'orderDate',
                    label: 'Datum',
                    formatter: date => this.$dateFormatter.toDateString(date),
                    sortable: true,
                },
                {
                    key: 'orderStatus',
                    label: 'Status',
                    formatter: status => (status === 'OPEN' ? 'Offen' : 'Geliefert'),
                    sortable: true,
                },
                { key: 'customerEmail', label: 'Kunde', sortable: true },
                {
                    key: 'paid',
                    label: 'Bezahlt',
                    formatter: paid => (paid ? 'Ja' : 'Nein'),
                    sortable: true,
                },
                {
                    key: 'orderItems',
                    label: `Bestellte\xa0Ware(n)`, // insert non-breakable space (need to use hexadecimal value of html entity)
                    formatter: products => {
                        let result = ''
                        products.forEach(product => {
                            result += `Produkt: ${product.articleName}
                            Menge: ${product.quantity}
                            Stückpreis: ${this.$currencyConverter.convertCentsToEuro(product.articlePrice)}
                            Gesamt: ${this.$currencyConverter.convertCentsToEuro(product.price)}
                            ` // append a line break before next item
                        })
                        // remove last line break
                        return result.slice(0, -1)
                    },
                    sortable: true,
                },
                {
                    key: 'shippingAddress',
                    label: 'Lieferadresse',
                    formatter: address => this.formatAddress(address),
                    sortable: true,
                },
                {
                    key: 'invoiceAddress',
                    label: 'Rechnungsadresse',
                    formatter: address => this.formatAddress(address),
                    sortable: true,
                },
                {
                    key: 'paymentMethod',
                    label: 'Zahlungsart',
                    formatter: method => {
                        switch (method) {
                            case 'CREDIT_CARD':
                                return 'Kreditkarte'
                            case 'PAYPAL':
                                return 'Paypal'
                            default:
                                return 'Rechnung'
                        }
                    },
                    sortable: true,
                },
                {
                    key: 'shippingMethod',
                    label: 'Versandart',
                    formatter: method => (method === 'DEFAULT' ? 'Standard' : 'Express'),
                    sortable: true,
                },
            ],
        }
    },
    methods: {
        formatAddress(address) {
            return `${address.firstName} ${address.lastName}
            ${address.address}
            ${address.zip} ${address.city}
            ${address.country}`
        },
    },
}
</script>

<style scoped></style>
