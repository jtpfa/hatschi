<template>
    <order-overview-card :step-to-edit-data="1" :title="title">
        <div class="row mb-2">
            <div class="col-sm-3 col-md-4 col-lg-3 col-xl-2">
                <span class="font-weight-bold">Name:</span>
            </div>
            <div class="col-sm-9 col-md-8 col-lg-9 col-xl-10">
                <b-card-text text-tag="span">
                    {{ order[`${addressType}Address`].firstName }} {{ order[`${addressType}Address`].lastName }}
                </b-card-text>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-md-4 col-lg-3 col-xl-2">
                <span class="font-weight-bold">Anschrift:</span>
            </div>
            <div class="col-sm-9 col-md-8 col-lg-9 col-xl-10">
                <b-card-text text-tag="span">
                    {{ order[`${addressType}Address`].street }} {{ order[`${addressType}Address`].number }}
                </b-card-text>
                <br />
                <b-card-text text-tag="span">
                    {{ order[`${addressType}Address`].zip }} {{ order[`${addressType}Address`].city }}
                </b-card-text>
                <br />
                <b-card-text v-if="order[`${addressType}Address`].additionalAddress" text-tag="span">
                    {{ order[`${addressType}Address`].additionalAddress }}
                </b-card-text>
                <br v-if="order[`${addressType}Address`].additionalAddress" />
                <b-card-text text-tag="span">
                    {{ order[`${addressType}Address`].country }}
                </b-card-text>
            </div>
        </div>
    </order-overview-card>
</template>

<script>
import OrderOverviewCard from '~/components/shop/order/steps/step3/card'

export default {
    name: 'OrderOverviewAddress',
    components: { OrderOverviewCard },
    props: {
        addressType: {
            type: String,
            required: true,
            validator(type) {
                // The value must match one of these strings
                return ['shipping', 'invoice'].indexOf(type) !== -1
            },
        },
        title: {
            type: String,
            required: true,
        },
    },
    computed: {
        order() {
            return this.$store.state.order
        },
    },
}
</script>

<style scoped></style>
