<template>
    <fetch-content v-if="$fetchState.pending" :size="6" />
    <div v-else>
        <b-alert v-if="fetchErrorMsg.length > 0" :show="true" variant="warning" v-html="fetchErrorMsg" />

        <b-alert v-else-if="orders.length === 0" :show="true" variant="info">Keine Bestellungen getätigt.</b-alert>

        <b-card
            v-for="item in orders"
            v-else
            :key="item.id"
            footer-class="d-flex flex-column flex-md-row justify-content-md-between"
            header-class="d-flex flex-column flex-md-row justify-content-md-between"
            no-body
        >
            <template v-slot:header>
                <div>
                    <span class="font-weight-bold">Bestellung aufgegeben</span>
                    <span>{{ $dateFormatter.toDateString(item.orderDate) }}</span>
                </div>
                <div>
                    <span class="font-weight-bold">Status</span>
                    <span>{{ item.orderStatus === 'OPEN' ? 'Offen' : 'Geliefert' }}</span>
                </div>
                <div>
                    <span class="font-weight-bold">Summe</span>
                    <span>
                        {{
                            $currencyConverter.convertCentsToEuro(
                                item.orderItems.reduce((ac, next) => ac + next.price, 0)
                            )
                        }}
                    </span>
                </div>
                <div>
                    <span class="font-weight-bold">Bezahlt</span>
                    <span>{{ item.paid ? 'Ja' : 'Nein' }}</span>
                </div>
                <div class="text-muted">
                    <span>Bestellnr.</span>
                    <span class="float-md-right">{{ item.id }}</span>
                </div>
            </template>

            <b-card-body body-class="py-0">
                <ul class="list-unstyled">
                    <b-media v-for="oItem in item.orderItems" :key="oItem.articleId" class="product py-4" tag="li">
                        <template v-slot:aside>
                            <div class="lazy-image">
                                <b-card-img-lazy
                                    :alt="oItem.articleName"
                                    onerror="this.onerror=null;this.srcset='/img/logo-placeholder.svg';"
                                    :src="$imageSrcSet.getImageUrl(oItem.articleId, 256)"
                                    :srcset="$imageSrcSet.getSrcSet(oItem.articleId)"
                                    :title="oItem.articleName"
                                />
                                <spinner />
                            </div>
                        </template>
                        <div class="product-information">
                            <span class="font-weight-bolder">{{ oItem.articleName }}</span>
                            <span>{{ $currencyConverter.convertCentsToEuro(oItem.articlePrice) }}</span>
                            <b-badge class="quantity" pill variant="primary">{{ oItem.quantity }} Stück</b-badge>
                        </div>
                    </b-media>
                </ul>
            </b-card-body>

            <template v-slot:footer>
                <div>
                    <span class="font-weight-bold">Lieferadresse</span>
                    <b-link
                        v-b-popover.bottom.hover.focus="{
                            title: `${item.shippingAddress.firstName} ${item.shippingAddress.lastName}`,
                            content: `${formatAddress(item.shippingAddress)}`,
                        }"
                        class="details"
                        variant="outline-primary"
                    >
                        {{ item.shippingAddress.firstName }} {{ item.shippingAddress.lastName }}
                    </b-link>
                </div>
                <div>
                    <span class="font-weight-bold">Rechnungsadresse</span>
                    <b-link
                        v-b-popover.bottom.hover.focus="{
                            title: `${item.invoiceAddress.firstName} ${item.invoiceAddress.lastName}`,
                            content: `${formatAddress(item.invoiceAddress)}`,
                        }"
                        class="details"
                        variant="outline-primary"
                    >
                        {{ item.invoiceAddress.firstName }} {{ item.invoiceAddress.lastName }}
                    </b-link>
                </div>
                <div>
                    <span class="font-weight-bold">Zahlungsart</span>
                    <span>{{ getPaymentMethod(item.paymentMethod) }}</span>
                </div>
                <div>
                    <span class="font-weight-bold">Versandart</span>
                    <span>{{ item.shippingAddress === 'DEFAULT' ? 'Standard' : 'Express' }}</span>
                </div>
            </template>
        </b-card>
    </div>
</template>

<script>
import FetchContent from '~/components/shop/layout/fetchContent'
import Spinner from '~/components/shop/layout/spinner'

export default {
    name: 'AccountOrders',
    components: { Spinner, FetchContent },
    async fetch() {
        try {
            this.orders = await this.$api.getAllOrdersOfCustomer(this.$auth.getToken('keycloak'))
        } catch (err) {
            this.fetchErrorMsg =
                err.message || 'Leider gab es ein Problem beim Laden der Daten. Bitte später erneut versuchen.'
        }
    },
    data() {
        return {
            orders: [],
            fetchErrorMsg: '',
        }
    },
    methods: {
        formatAddress(address) {
            return `${address.address}
            ${address.additionalAddress ? address.additionalAddress : ''}
            ${address.zip} ${address.city}
            ${address.country}`
        },
        getPaymentMethod(method) {
            switch (method) {
                case 'CREDIT_CARD':
                    return 'Kreditkarte'
                case 'PAYPAL':
                    return 'Paypal'
                default:
                    return 'Rechnung'
            }
        },
    },
}
</script>

<style lang="scss" scoped>
::v-deep .popover-body {
    white-space: pre-line;
}

a.details {
    &:hover,
    &:focus {
        text-decoration: none;
    }
}

.card-header,
.card-footer {
    > div:not(:last-child) {
        margin-bottom: 0.75rem;

        @media (min-width: $grid-md) {
            margin-bottom: 0;
        }
    }
}

.card-header,
.card-footer,
.product-information {
    span:not(.quantity) {
        display: block;
    }
}

.product:not(:last-of-type) {
    border-bottom: 1px solid $gray-300;
}

.product-information {
    span:not(:last-child) {
        margin-bottom: 0.75rem;
    }
}

.lazy-image {
    position: relative;
    flex-shrink: 0;
    width: 7.5rem;
    overflow: hidden;

    img {
        display: block;
        max-width: 80%;
    }
}

.quantity {
    font-size: 1rem;
}
</style>
