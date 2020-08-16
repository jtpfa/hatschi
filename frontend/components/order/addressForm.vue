<template>
    <div>
        <h2>{{ addressType === 'delivery' ? 'Lieferadresse' : 'Rechnungsadresse' }} angeben</h2>
        <b-card>
            <div class="row">
                <!-- @todo insert first and last name when user is logged in  -->
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
                        placeholder="PLZ"
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Bitte gib eine PLZ an.
                    </b-form-invalid-feedback>
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
                        placeholder="Stadt"
                        required
                        trim
                    />
                </div>

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib eine Stadt an.
                </b-form-invalid-feedback>
            </div>

            <div class="row">
                <div class="mb-4 col-sm-8 col-xl-9" role="group">
                    <label :for="`${addressType}-street`">
                        Straße
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        :id="`${addressType}-street`"
                        v-model="street"
                        aria-describedby="input-live-feedback"
                        placeholder="Straße"
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Bitte gib eine Straße an.
                    </b-form-invalid-feedback>
                </div>

                <div class="mb-4 col-sm-4 col-xl-3" role="group">
                    <label :for="`${addressType}-number`">
                        Hausnr.
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        :id="`${addressType}-number`"
                        v-model="number"
                        aria-describedby="input-live-feedback"
                        placeholder="Hausnr."
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Bitte gib eine Hausnummer an.
                    </b-form-invalid-feedback>
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
                return ['delivery', 'invoice'].indexOf(type) !== -1
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
        street: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`].street
            },
            set(street) {
                this.$store.commit('order/updateOrderInformation', {
                    address: `${this.addressType}Address`,
                    key: 'street',
                    data: street,
                })
            },
        },
        number: {
            get() {
                return this.$store.state.order[`${this.addressType}Address`].number
            },
            set(number) {
                this.$store.commit('order/updateOrderInformation', {
                    address: `${this.addressType}Address`,
                    key: 'number',
                    data: number,
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
