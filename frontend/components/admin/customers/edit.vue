<template>
    <b-modal :id="modalId" centered hide-footer lazy scrollable title="Kundendaten bearbeiten">
        <b-form ref="form" novalidate @submit.prevent="onSubmit">
            <div class="mb-4" role="group">
                <label for="email">
                    E-Mail
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="email"
                    v-model="username"
                    aria-describedby="input-live-feedback"
                    pattern="^.{4,100}$"
                    placeholder="E-Mail"
                    required
                    trim
                    type="email"
                />

                <b-form-invalid-feedback id="input-live-feedback">Bitte E-Mail angeben.</b-form-invalid-feedback>
            </div>

            <div class="mb-4" role="group">
                <label for="firstname">
                    Vorname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="firstname"
                    v-model="customer.firstName"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Vorname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">Bitte Vornamen angeben.</b-form-invalid-feedback>
            </div>

            <div class="mb-4" role="group">
                <label for="name">
                    Nachname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="name"
                    v-model="customer.lastName"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Nachname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">Bitte Nachnamen angeben.</b-form-invalid-feedback>
            </div>

            <b-alert class="mt-3" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

            <button-container :loading="loading" text="Daten speichern" />
        </b-form>
    </b-modal>
</template>

<script>
import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'CustomerEdit',
    components: { ButtonContainer },
    props: {
        customer: {
            type: Object,
            required: true,
        },
        modalId: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            newEmail: this.customer.email,
            error: '',
            loading: false,
        }
    },
    computed: {
        username: {
            get() {
                return this.newEmail ? this.newEmail : this.customer.email
            },
            set(newEmail) {
                this.newEmail = newEmail
            },
        },
    },
    methods: {
        async editCustomer() {
            try {
                await this.$api.editCustomer(
                    {
                        email: this.newEmail,
                        firstName: this.customer.firstName,
                        lastName: this.customer.lastName,
                    },
                    this.customer.email,
                    this.$auth.getToken('keycloak')
                )

                this.$root.$emit('bv::hide::modal', this.modalId)
                this.$router.app.refresh()
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
            } else {
                this.$refs.form.classList.add('was-validated')
                await this.editCustomer()
            }
            this.loading = false
        },
    },
}
</script>

<style scoped></style>
