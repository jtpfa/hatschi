<template>
    <b-modal
        :id="modalId"
        centered
        footer-class="login-footer"
        :hide-header-close="loginPage"
        lazy
        :no-close-on-backdrop="loginPage"
        :no-close-on-esc="loginPage"
        :no-fade="loginPage"
        ok-only
        scrollable
        title="Login"
    >
        <b-form ref="form" novalidate>
            <div class="mb-3" role="group">
                <label for="email">
                    E-Mail
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="email"
                    ref="email"
                    v-model="email"
                    autocomplete="email"
                    placeholder="E-Mail"
                    required
                    trim
                    type="email"
                ></b-form-input>
            </div>

            <div role="group">
                <label for="password">
                    Passwort
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="password"
                    ref="password"
                    v-model="password"
                    autocomplete="current-password"
                    placeholder="Passwort"
                    required
                    trim
                    type="password"
                ></b-form-input>
            </div>
        </b-form>

        <b-alert class="mt-5 mb-0" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

        <template v-slot:modal-footer>
            <div class="d-flex justify-content-between align-items-center w-100">
                <div class="big-noodle">
                    Noch kein Konto?
                    <b-link to="/auth/registrierung" @click="closeModal">Jetzt registrieren</b-link>
                </div>
                <b-button :disabled="loading" type="submit" variant="primary" @click.prevent="onSubmit">
                    <b-spinner v-if="loading" small></b-spinner>
                    Login
                </b-button>
            </div>
        </template>
    </b-modal>
</template>

<script>
export default {
    name: 'LoginForm',
    props: {
        modalId: {
            type: String,
            default: '',
        },
        loginPage: {
            type: Boolean,
            defaults: false,
        },
    },
    data() {
        return {
            email: '',
            password: '',
            error: '',
            loading: false,
        }
    },
    methods: {
        async onSubmit(event) {
            this.$refs.email.setCustomValidity('')
            this.$refs.password.setCustomValidity('')
            this.loading = true
            if (!this.$refs.form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
                this.$refs.form.classList.add('was-validated')
                this.loading = false
                return
            }
            this.$refs.form.classList.add('was-validated')

            try {
                await this.$auth.loginWith('keycloak', { username: this.email, password: this.password })
                this.$bvModal.hide(this.modalId)
            } catch (err) {
                this.$refs.email.setCustomValidity('Benutzername und Passwort stimmen nicht überein.')
                this.$refs.password.setCustomValidity('Benutzername und Passwort stimmen nicht überein.')
                this.error = 'Es ist ein Fehler passiert. Bitter später erneut versuchen.'
            }
            this.loading = false
        },
        closeModal() {
            this.$bvModal.hide(this.modalId)
        },
    },
}
</script>

<style lang="scss" scoped>
::v-deep .login-footer {
    border-top: none;
}
</style>
