<template>
    <b-form ref="form" novalidate @submit.prevent="onSubmit">
        <div class="row">
            <div class="mb-4 col-xl-6" role="group">
                <label :for="`firstName-${address.id}`">
                    Vorname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    :id="`firstName-${address.id}`"
                    v-model="address.firstName"
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
                <label :for="`lastName-${address.id}`">
                    Nachname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    :id="`lastName-${address.id}`"
                    v-model="address.lastName"
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
                <label :for="`zip-${address.id}`">
                    PLZ
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    :id="`zip-${address.id}`"
                    v-model="address.zip"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="PLZ"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">Bitte gib eine PLZ an.</b-form-invalid-feedback>
            </div>

            <div class="mb-4 col-sm-8" role="group">
                <label :for="`city-${address.id}`">
                    Stadt
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    :id="`city-${address.id}`"
                    v-model="address.city"
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
                <label :for="`address-${address.id}`">
                    Straße und Hausnr.
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    :id="`address-${address.id}`"
                    v-model="address.address"
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
                <label :for="`additionalAddress-${address.id}`">Adresszusatz</label>
                <b-form-input
                    :id="`additionalAddress-${address.id}`"
                    v-model="address.additionalAddress"
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
                <label :for="`country-${address.id}`">
                    Land
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    :id="`country-${address.id}`"
                    v-model="address.country"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Land"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">Bitte gib ein Land an.</b-form-invalid-feedback>
            </div>
        </div>

        <b-alert class="mt-3" :show="error.length > 0" variant="danger" v-html="error" />

        <button-container :loading="loading" text="Änderungen speichern" />
    </b-form>
</template>

<script>
/**
 * @component AccountEditAddress
 * @desc Form to edit a address in profil
 * @author Jonas Pfannkuche
 */

import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'AccountEditAddress',
    components: { ButtonContainer },
    props: {
        /**
         * @vprop {Object} address - Address which should be edited
         */
        address: {
            type: Object,
            required: true,
        },
    },
    data() {
        return {
            /**
             * @member {String} error - General error message
             */
            error: '',
            /**
             * @member {Boolean} loading - Request status
             */
            loading: false,
        }
    },
    methods: {
        /**
         * @method editAddress
         * @desc Calls api endpoint to edit address and handles response
         * @returns {Promise<void>}
         */
        async editAddress() {
            try {
                await this.$api.editCustomerAddress(
                    {
                        firstName: this.address.firstName,
                        lastName: this.address.lastName,
                        address: this.address.address,
                        additionalAddress: this.address.additionalAddress,
                        zip: this.address.zip,
                        city: this.address.city,
                        country: this.address.country,
                    },
                    this.address.id,
                    this.$auth.getToken('keycloak')
                )
                this.$root.$emit('bv::toggle::collapse', `overview-${this.address.id}`)
                this.$router.app.refresh()
                this.$refs.form.classList.remove('was-validated')
            } catch (err) {
                this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
            }
        },
        /**
         * @method onSubmit
         * @desc Validates the form, shows validation state and calls {@link component:AccountEditAddress~editAddress editAddress} if the form is valid
         * @param {Object} event - Browser event which is fired on submitting the form
         */
        async onSubmit(event) {
            this.loading = true
            this.error = ''
            if (!this.$refs.form.checkValidity()) {
                this.$refs.form.classList.add('was-validated')
                event.preventDefault()
                event.stopPropagation()
            } else {
                this.$refs.form.classList.add('was-validated')
                await this.editAddress()
            }
            this.loading = false
        },
    },
}
</script>

<style scoped></style>
