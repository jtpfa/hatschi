<template>
    <b-card title="Vouchers">
        <div class="row mb-2">
            <div class="col-lg-5">
                <span class="font-weight-bold">Geschenkkartencode:</span>
            </div>
            <div class="col-lg-7">
                <b-card-text v-if="order.giftCardCode.length > 0" text-tag="span">{{ order.giftCardCode }}</b-card-text>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-5">
                <span class="font-weight-bold">Aktionscode:</span>
            </div>
            <div class="col-lg-7">
                <b-card-text v-if="order.discountCode.length > 0" text-tag="span">{{ order.discountCode }}</b-card-text>
            </div>
        </div>
    </b-card>
</template>

<script>
export default {
    name: 'OrderOverviewVouchers',
    computed: {
        order() {
            return this.$store.state.order
        },
    },
}
</script>

<style scoped></style>
