<template>
    <div>
        <h2>{{ addressType === 'shipping' ? 'Lieferadresse' : 'Rechnungsadresse' }} angeben</h2>
        <b-card>
            <div class="row">
                <div class="mb-4 col-xl-6" role="group">
                    <label :for="`${addressType}-firstName`">
                        Vorname
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        :id="`${addressType}-firstName`"
                        v-model="firstName"
                        aria-describedby="input-live-feedback"
                        autocomplete="given-name"
                        pattern="^.{1,50}$"
                        placeholder="Vorname"
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Bitte gib deinen Vornamen an.
                    </b-form-invalid-feedback>
                </div>

                <div class="mb-4 col-xl-6" role="group">
                    <label :for="`${addressType}-lastName`">
                        Nachname
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        :id="`${addressType}-lastName`"
                        v-model="lastName"
                        aria-describedby="input-live-feedback"
                        autocomplete="family-name"
                        pattern="^.{1,50}$"
                        placeholder="Nachname"
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Bitte gib deinen Nachnamen an.
                    </b-form-invalid-feedback>
                </div>
            </div>

            <div class="row">
                <div class="mb-4 col-sm-4" role="group">
                    <label :for="`${addressType}-zip`">
                        PLZ
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        :id="`${addressType}-zip`"
                        v-model="zip"
                        aria-describedby="input-live-feedback"
                        pattern="^.{1,50}$"
                        placeholder="PLZ"
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">Bitte gib eine PLZ an.</b-form-invalid-feedback>
                </div>

                <div class="mb-4 col-sm-8" role="group">
                    <label :for="`${addressType}-city`">
                        Stadt
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        :id="`${addressType}-city`"
                        v-model="city"
                        aria-describedby="input-live-feedback"
                        pattern="^.{1,100}$"
                        placeholder="Stadt"
                        required
                        trim
                    />
                </div>

                <b-form-invalid-feedback id="input-live-feedback">Bitte gib eine Stadt an.</b-form-invalid-feedback>
            </div>

            <div class="row">
                <div class="mb-4 col-12" role="group">
                    <label :for="`${addressType}-address`">
                        Straße und Hausnr.
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        :id="`${addressType}-address`"
                        v-model="address"
                        aria-describedby="input-live-feedback"
                        pattern="^.{1,255}$"
                        placeholder="Straße und Hausnr."
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Bitte gib eine Straße inkl. Hausnr. an.
                    </b-form-invalid-feedback>
                </div>
            </div>

            <div class="row">
                <div class="mb-4 col-sm-4 col-xl-3" role="group">
                    <label :for="`${addressType}-additional-address`">Adresszusatz</label>
                    <b-form-input
                        :id="`${addressType}-additional-address`"
                        v-model="additionalAddress"
                        aria-describedby="input-live-feedback"
                        pattern="^.{1,255}$"
                        placeholder="Zusatz"
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Bitte gib einen Adresszusatz an.
                    </b-form-invalid-feedback>
                </div>

                <div class="mb-4 col-sm-8 col-xl-9" role="group">
                    <label :for="`${addressType}-country`">
                        Land
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        :id="`${addressType}-country`"
                        v-model="country"
                        aria-describedby="input-live-feedback"
                        pattern="^.{1,50}$"
                        placeholder="Land"
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">Bitte gib ein Land an.</b-form-invalid-feedback>
                </div>
            </div>
        </b-card>
    </div>
</template>

<script>
export default {
    name: 'OrderAddressForm',
    props: {
        addressType: {
            type: String,
            required: true,
            validator(type) {
                // The value must match one of these strings
                return ['shipping', 'invoice'].indexOf(type) !== -1
            },
        },
    },
    computed: {
        firstName: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`].firstName
            },
            set(firstName) {
                this.$store.commit('order/updateOrderInformation', {
                    address: `${this.addressType}Address`,
                    key: 'firstName',
                    data: firstName,
                })
            },
        },
        lastName: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`].lastName
            },
            set(lastName) {
                this.$store.commit('order/updateOrderInformation', {
                    address: `${this.addressType}Address`,
                    key: 'lastName',
                    data: lastName,
                })
            },
        },
        zip: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`].zip
            },
            set(zip) {
                this.$store.commit('order/updateOrderInformation', {
                    address: `${this.addressType}Address`,
                    key: 'zip',
                    data: zip,
                })
            },
        },
        city: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`].city
            },
            set(city) {
                this.$store.commit('order/updateOrderInformation', {
                    address: `${this.addressType}Address`,
                    key: 'city',
                    data: city,
                })
            },
        },
        address: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`].address
            },
            set(address) {
                this.$store.commit('order/updateOrderInformation', {
                    address: `${this.addressType}Address`,
                    key: 'address',
                    data: address,
                })
            },
        },
        additionalAddress: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`].additionalAddress
            },
            set(additionalAddress) {
                this.$store.commit('order/updateOrderInformation', {
                    address: `${this.addressType}Address`,
                    key: 'additionalAddress',
                    data: additionalAddress,
                })
            },
        },
        country: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`].country
            },
            set(country) {
                this.$store.commit('order/updateOrderInformation', {
                    address: `${this.addressType}Address`,
                    key: 'country',
                    data: country,
                })
            },
        },
    },
}
</script>

<style lang="scss" scoped>
label {
    font-size: 1.25rem;
}
</style>
