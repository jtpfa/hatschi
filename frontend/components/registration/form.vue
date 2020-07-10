<template>
    <b-form ref="form" novalidate @submit.prevent="onSubmit">
        <b-alert class="my-3" :show="success.length > 0" variant="success">{{ success }}</b-alert>

        <b-form-row class="mb-md-4">
            <div class="col-md-6 mb-4 mb-md-0" role="group">
                <label for="firstname">
                    Vorname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="firstname"
                    v-model="firstName"
                    aria-describedby="input-live-feedback"
                    autocomplete="given-name"
                    pattern="^.{2,50}$"
                    placeholder="Vorname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib deinen Vornamen an.
                </b-form-invalid-feedback>
            </div>
            <div class="col-md-6 mb-4 mb-md-0" role="group">
                <label for="name">
                    Nachname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="name"
                    v-model="lastName"
                    aria-describedby="input-live-feedback"
                    autocomplete="family-name"
                    pattern="^.{2,50}$"
                    placeholder="Nachname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib deinen Nachnamen an.
                </b-form-invalid-feedback>
            </div>
        </b-form-row>

        <div class="mb-4" role="group">
            <label for="email">
                E-Mail
                <span class="mandatory">*</span>
            </label>
            <b-form-input
                id="email"
                v-model="email"
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

        <form-field-password-confirmation ref="passwordConfirmation" />

        <b-alert class="mt-3" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

        <div class="button-container">
            <b-button :disabled="loading" type="submit" variant="primary">
                <b-spinner v-if="loading" small></b-spinner>
                Registrieren
            </b-button>
        </div>
    </b-form>
</template>

<script>
import FormFieldPasswordConfirmation from '~/components/form-fields/passwordConfirmation'

export default {
    name: 'RegistrationForm',
    components: { FormFieldPasswordConfirmation },
    data() {
        return {
            firstName: '',
            lastName: '',
            email: '',
            success: '',
            error: '',
            loading: false,
        }
    },
    methods: {
        async register() {
            try {
                await this.$api.signUp({
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email,
                    password: this.$refs.passwordConfirmation.password,
                })
                this.success = 'Dein Benutzer wurde erfolgreich angelegt.'
            } catch (err) {
                this.error = 'Leider gab es ein Problem. Bitte versuch es später erneut.'
            }
        },
        onSubmit(event) {
            this.loading = true
            this.success = ''
            this.error = ''
            if (!this.$refs.form.checkValidity() || !this.$refs.passwordConfirmation.isPasswordConfirmed()) {
                event.preventDefault()
                event.stopPropagation()
            } else {
                this.register()
            }
            this.loading = false
            this.$refs.form.classList.add('was-validated')
        },
    },
}
</script>

<style lang="scss" scoped>
.button-container {
    display: flex;
    flex-wrap: wrap;
    flex-direction: column-reverse;

    @media (min-width: $grid-sm) {
        flex-direction: row;
        align-items: center;
        justify-content: flex-end;
    }

    .btn {
        font-size: 1.2rem;
    }

    > * {
        width: 100%;
        margin: 1rem 0 0 0;

        @media (min-width: $grid-sm) {
            width: auto;
            margin: 0 0 0 1rem;
        }
    }
}
</style>
