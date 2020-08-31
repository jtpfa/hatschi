<template>
    <div>
        <b-alert class="my-3" :show="success.length > 0" variant="success">
            {{ success }}
            <template v-if="emailChanged">
                Bitte mit den neuen Anmeldedaten
                <b-link class="alert-link>" @click="login">einloggen.</b-link>
            </template>
        </b-alert>

        <b-form v-if="!emailChanged" ref="form" novalidate @submit.prevent="onSubmit">
            <div class="mb-4" role="group">
                <label for="firstname">
                    Vorname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="firstname"
                    v-model="user.firstName"
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

            <div class="mb-4" role="group">
                <label for="name">
                    Nachname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="name"
                    v-model="user.lastName"
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

            <div class="mb-4" role="group">
                <label for="email">
                    E-Mail
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="email"
                    v-model="user.email"
                    aria-describedby="input-live-feedback"
                    autocomplete="email"
                    pattern="^.{4,100}$"
                    placeholder="E-Mail"
                    required
                    trim
                    type="email"
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib eine gültige E-Mail Adresse an.
                </b-form-invalid-feedback>
            </div>

            <b-alert class="mt-3" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

            <button-container :loading="loading" text="Profildaten ändern" />
        </b-form>
    </div>
</template>

<script>
import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'ProfilPersonalData',
    components: { ButtonContainer },
    data() {
        return {
            user: {
                firstName: this.$auth.user.firstName,
                lastName: this.$auth.user.lastName,
                email: this.$auth.user.email,
            },
            success: '',
            error: '',
            loading: false,
            emailChanged: false,
        }
    },
    methods: {
        async updateUserData() {
            try {
                await this.$api.updateUserData(this.user, this.$auth.getToken('keycloak'))
                this.success = 'Deine Benutzerdaten wurde erfolgreich geändert.'

                if (this.$auth.user.email !== this.user.email) {
                    this.emailChanged = true
                }
            } catch (err) {
                this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
            }
        },
        async onSubmit(event) {
            this.loading = true
            this.success = ''
            this.error = ''
            if (!this.$refs.form.checkValidity()) {
                this.$refs.form.classList.add('was-validated')
                event.preventDefault()
                event.stopPropagation()
            } else {
                this.$refs.form.classList.add('was-validated')
                await this.updateUserData()
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
