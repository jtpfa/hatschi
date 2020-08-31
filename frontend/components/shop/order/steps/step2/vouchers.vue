<template>
    <div>
        <h2>Vouchers</h2>
        <b-card>
            <div class="row">
                <div class="mb-4 col-xl-6" role="group">
                    <label for="giftCardCode">Geschenkkartencode</label>
                    <b-form-input
                        id="giftCardCode"
                        v-model="giftCardCode"
                        aria-describedby="input-live-feedback"
                        placeholder="XYZ-1234-5678-9011"
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Bitte gib einen gültigen Code an.
                    </b-form-invalid-feedback>
                </div>
                <div class="mb-4 col-xl-6" role="group">
                    <label for="discountCode">Aktionscode</label>
                    <b-form-input
                        id="discountCode"
                        v-model="discountCode"
                        aria-describedby="input-live-feedback"
                        placeholder="123-456-789-XYZ"
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Bitte gib einen gültigen Code an.
                    </b-form-invalid-feedback>
                </div>
            </div>
        </b-card>
    </div>
</template>

<script>
export default {
    name: 'OrderVouchers',
    computed: {
        giftCardCode: {
            get() {
                return this.$store.state.order.giftCardCode
            },
            set(giftCardCode) {
                this.$store.commit('order/updateOrderInformation', {
                    key: 'giftCardCode',
                    data: giftCardCode,
                })
            },
        },
        discountCode: {
            get() {
                return this.$store.state.order.discountCode
            },
            set(discountCode) {
                this.$store.commit('order/updateOrderInformation', {
                    key: 'discountCode',
                    data: discountCode,
                })
            },
        },
    },
}
</script>

<style scoped></style>
