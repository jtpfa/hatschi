<template>
    <order-overview-card :step-to-edit-data="1" :title="title">
        <div class="row mb-2">
            <div class="col-sm-3 col-md-4 col-lg-3 col-xl-2">
                <span class="font-weight-bold">Name:</span>
            </div>
            <div class="col-sm-9 col-md-8 col-lg-9 col-xl-10">
                <b-card-text text-tag="span">
                    {{ order.addresses[addressId].firstName }}
                    {{ order.addresses[addressId].lastName }}
                </b-card-text>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-md-4 col-lg-3 col-xl-2">
                <span class="font-weight-bold">Anschrift:</span>
            </div>
            <div class="col-sm-9 col-md-8 col-lg-9 col-xl-10">
                <b-card-text text-tag="span">
                    {{ order.addresses[addressId].address }}
                </b-card-text>
                <br />
                <b-card-text text-tag="span">
                    {{ order.addresses[addressId].zip }}
                    {{ order.addresses[addressId].city }}
                </b-card-text>
                <br />
                <b-card-text v-if="order.addresses[addressId].additionalAddress" text-tag="span">
                    {{ order.addresses[addressId].additionalAddress }}
                </b-card-text>
                <br v-if="order.addresses[addressId].additionalAddress" />
                <b-card-text text-tag="span">
                    {{ order.addresses[addressId].country }}
                </b-card-text>
            </div>
        </div>
    </order-overview-card>
</template>

<script>
/**
 * @component OrderOverviewAddress
 * @desc Overview of the shipping OR invoice address
 * @author Jonas Pfannkuche
 */

import OrderOverviewCard from '~/components/shop/order/steps/step3/card'

export default {
    name: 'OrderOverviewAddress',
    components: { OrderOverviewCard },
    props: {
        /**
         * @vprop {'shipping'|'invoice'} - Type of the address
         */
        addressType: {
            type: String,
            required: true,
            validator(type) {
                // The value must match one of these strings
                return ['shipping', 'invoice'].indexOf(type) !== -1
            },
        },
        /**
         * @vprop {String} title - Title to show as descriptional header
         */
        title: {
            type: String,
            required: true,
        },
    },
    computed: {
        /**
         * @computed {Object} order - All order related information: {@link store:Order}
         */
        order() {
            return this.$store.state.order
        },
        /**
         * @computed {Number} addressId - Id of the selected address
         */
        addressId() {
            return +this.order[`${this.addressType}Address`].index
        },
    },
}
</script>

<style scoped></style>
