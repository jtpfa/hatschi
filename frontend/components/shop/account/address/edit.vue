<template>
    <b-form ref="form" novalidate @submit.prevent="onSubmit">
        <div class="row">
            <div class="mb-4 col-xl-6" role="group">
                <label for="firstName">
                    Vorname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="firstName"
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
                <label for="lastName">
                    Nachname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="lastName"
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
                <label for="zip">
                    PLZ
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="zip"
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
                <label for="city">
                    Stadt
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="city"
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
                <label for="address">
                    Straße und Hausnr.
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="address"
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
                <label for="additionalAddress">Adresszusatz</label>
                <b-form-input
                    id="additionalAddress"
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
                <label for="country">
                    Land
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="country"
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
import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'AccountEditAddress',
    components: { ButtonContainer },
    props: {
        address: {
            type: Object,
            required: true,
        },
    },
    data() {
        return {
            loading: false,
            error: '',
        }
    },
    methods: {
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
