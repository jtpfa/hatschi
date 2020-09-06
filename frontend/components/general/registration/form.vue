<template>
    <div>
        <b-alert class="my-3" :show="success.length > 0" variant="success">
            {{ success }}
            <b-link to="/auth/login">Zum Login</b-link>
        </b-alert>

        <b-form v-if="success.length === 0" ref="form" novalidate @submit.prevent="onSubmit">
            <div class="mb-4" role="group">
                <label for="firstname">
                    Vorname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="firstname"
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
            <div class="mb-4" role="group">
                <label for="name">
                    Nachname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="name"
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

            <b-alert class="mt-3" :show="error.length > 0" variant="danger" v-html="error" />

            <button-container :loading="loading" text="Registrieren" />
        </b-form>
    </div>
</template>

<script>
/**
 * @component RegistrationForm
 * @desc Registration form
 * @author Jonas Pfannkuche
 */

import FormFieldPasswordConfirmation from '~/components/general/form-fields/passwordConfirmation'
import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'RegistrationForm',
    components: { ButtonContainer, FormFieldPasswordConfirmation },
    data() {
        return {
            /**
             * @member {String} firstName - First name of the new user
             */
            firstName: '',
            /**
             * @member {String} lastName - Last name of the new user
             */
            lastName: '',
            /**
             * @member {String} email - Email of the new user
             */
            email: '',
            /**
             * @member {String} success - General success message
             */
            success: '',
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
         * @method register
         * @desc Calls api endpoint to register a new user
         * @returns {Promise<void>}
         */
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
                this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
            }
        },
        /**
         * @method onSubmit
         * @desc Validates the form, shows validation state and calls {@link component:RegisterForm~register register} if the form is valid
         * @param {Object} event - Browser event which is fired on submitting the form
         */
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
                await this.register()
            }
            this.loading = false
        },
    },
}
</script>

<style lang="scss" scoped></style>
