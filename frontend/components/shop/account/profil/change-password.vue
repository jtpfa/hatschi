<template>
    <div>
        <b-alert class="my-3" :show="success.length > 0" variant="success">
            {{ success }}
            Bitte mit den neuen Anmeldedaten
            <b-link class="alert-link>" @click="login">einloggen.</b-link>
        </b-alert>

        <b-form v-if="success.length === 0" ref="form" novalidate @submit.prevent="onSubmit">
            <div class="mb-4" role="group">
                <label for="currentPassword">
                    Aktuelles Passwort
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="currentPassword"
                    v-model="currentPassword"
                    aria-describedby="input-live-feedback"
                    autocomplete="current-password"
                    pattern="^.{6,120}$"
                    placeholder="Aktuelles Passwort"
                    required
                    trim
                    type="password"
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib dein aktuelles Passwort an.
                </b-form-invalid-feedback>
            </div>

            <form-field-password-confirmation ref="passwordConfirmation" />

            <b-alert class="mt-3" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

            <button-container :loading="loading" text="Passwort ändern" />
        </b-form>
    </div>
</template>

<script>
import FormFieldPasswordConfirmation from '~/components/general/form-fields/passwordConfirmation'
import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'ProfilChangePassword',
    components: { FormFieldPasswordConfirmation, ButtonContainer },
    data() {
        return {
            currentPassword: '',
            success: '',
            error: '',
            loading: false,
        }
    },
    methods: {
        async changePassword() {
            try {
                await this.$api.changeUserPassword(
                    {
                        currentPassword: this.currentPassword,
                        newPassword: this.$refs.passwordConfirmation.password,
                        confirmation: this.$refs.passwordConfirmation.password,
                    },
                    this.$auth.getToken('keycloak')
                )
                this.success = 'Dein Passwort wurde erfolgreich geändert.'
            } catch (err) {
                this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
            }
        },
        async onSubmit(event) {
            this.loading = true
            this.success = ''
            this.error = ''
            if (!this.$refs.form.checkValidity() || !this.$refs.passwordConfirmation.isPasswordConfirmed()) {
                this.$refs.form.classList.add('was-validated')
                event.preventDefault()
                event.stopPropagation()
            } else {
                this.$refs.form.classList.add('was-validated')
                await this.changePassword()
            }
            this.loading = false
        },
        async login() {
            window.location.href = `${this.$config.keycloakLogoutEndpoint}?redirect_uri=${encodeURI(
                `${this.$config.baseURL}/auth/login`
            )}`
            this.$auth.reset()
        },
    },
}
</script>

<style scoped></style>
