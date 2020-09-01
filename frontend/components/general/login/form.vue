<template>
    <div :class="{ 'pb-4 mx-4 col-11 col-sm-9 col-md-7 col-lg-5 col-xl-4 bg-white border': loginPage }">
        <b-form ref="form" class="pb-3 px-3" novalidate @submit.prevent="onSubmit">
            <b-alert class="my-5" :show="dashboard && !hasAccess" variant="warning">Zugriff nicht gestattet.</b-alert>
            <b-button-close v-if="loginPage" @click="$router.push('/')" />
            <div class="mb-3" :class="{ 'mt-3': !loginPage, 'mt-5': loginPage }" role="group">
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

            <div class="mb-4" role="group">
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

            <b-alert class="mt-5 mb-3" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

            <div class="d-flex justify-content-between align-items-center w-100">
                <div class="big-noodle">
                    Noch kein Konto?
                    <b-link to="/auth/registrierung" @click="$emit('register')">Jetzt registrieren</b-link>
                </div>
                <b-button :disabled="loading" type="submit" variant="primary">
                    <b-spinner v-if="loading" small></b-spinner>
                    Login
                </b-button>
            </div>
        </b-form>
    </div>
</template>

<script>
export default {
    name: 'LoginForm',
    props: {
        loginPage: {
            type: Boolean,
            default: false,
        },
        dashboard: {
            type: Boolean,
            default: false,
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
    computed: {
        hasAccess() {
            return this.$auth.loggedIn && ['employee', 'admin'].includes(this.$auth.roles)
        },
    },
    methods: {
        async onSubmit(event) {
            this.loading = true
            this.$refs.email?.setCustomValidity('')
            this.$refs.password?.setCustomValidity('')
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
                this.$emit('success')
            } catch (err) {
                this.$refs.email?.setCustomValidity('Benutzername und Passwort stimmen nicht überein.')
                this.$refs.password?.setCustomValidity('Benutzername und Passwort stimmen nicht überein.')
                this.error = err.message || 'Es ist ein Fehler passiert. Bitter später erneut versuchen.'
            }
            this.loading = false
        },
    },
}
</script>

<style lang="scss" scoped></style>
