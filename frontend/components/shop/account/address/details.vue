<template>
    <div>
        <div class="row mb-2">
            <div class="col-sm-3 col-md-4 col-lg-3">
                <span class="font-weight-bold">Name:</span>
            </div>
            <div class="col-sm-9 col-md-8 col-lg-9">
                <b-card-text text-tag="span">{{ address.firstName }} {{ address.lastName }}</b-card-text>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-md-4 col-lg-3">
                <span class="font-weight-bold">Anschrift:</span>
            </div>
            <div class="col-sm-9 col-md-8 col-lg-9">
                <b-card-text text-tag="span">
                    {{ address.address }}
                </b-card-text>
                <br />
                <b-card-text text-tag="span">{{ address.zip }} {{ address.city }}</b-card-text>
                <br />
                <b-card-text v-if="address.additionalAddress" text-tag="span">
                    {{ address.additionalAddress }}
                </b-card-text>
                <br v-if="address.additionalAddress" />
                <b-card-text text-tag="span">
                    {{ address.country }}
                </b-card-text>
            </div>
        </div>
    </div>
</template>

<script>
/**
 * @component AccountAddAddress
 * @desc Card view of one address
 * @author Jonas Pfannkuche
 */

export default {
    name: 'AccountAddressDetails',
    props: {
        /**
         * @vprop {Object} address - Address to show
         */
        address: {
            type: Object,
            required: true,
        },
    },
}
</script>

<style scoped></style>
