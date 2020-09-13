<template>
    <div>
        <b-form ref="form" novalidate @submit.prevent="onSubmit">
            <b-form-input id="username" v-model="$auth.user.email" autocomplete="username" hidden type="text" />
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

            <button-container :loading="loading" text="Passwort ändern" />
        </b-form>
    </div>
</template>

<script>
/**
 * @component ProfilChangePassword
 * @desc Form to change user password (current password and new password with confirmation)
 * @author Jonas Pfannkuche
 */

import FormFieldPasswordConfirmation from '~/components/general/form-fields/passwordConfirmation'
import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'ProfilChangePassword',
    components: { FormFieldPasswordConfirmation, ButtonContainer },
    data() {
        return {
            /**
             * @member {String} currentPassword - Current password of user
             */
            currentPassword: '',
            /**
             * @member {Boolean} loading - Request status
             */
            loading: false,
        }
    },
    methods: {
        /**
         * @method changePassword
         * @desc Calls api endpoint to change user password and handles response
         * @returns {Promise<void>}
         */
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
                this.$emit('success', 'Dein Passwort wurde erfolgreich geändert.')
                this.$emit('loginDataChanged')
            } catch (err) {
                this.$emit('error', err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.')
            }
        },
        /**
         * @method onSubmit
         * @desc Validates the form, shows validation state and calls {@link component:ProfilChangePassword~changePassword changePassword} if the form is valid
         * @param {Object} event - Browser event which is fired on submitting the form
         */
        async onSubmit(event) {
            this.loading = true
            this.$emit('reset')
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
    },
}
</script>

<style scoped></style>
