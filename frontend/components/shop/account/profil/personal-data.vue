<template>
    <div>
        <b-form ref="form" novalidate @submit.prevent="onSubmit">
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

            <button-container :loading="loading" text="Profildaten ändern" />
        </b-form>
    </div>
</template>

<script>
/**
 * @component ProfilPersonalData
 * @desc Form to change user data (first name, last name and email)
 * @author Jonas Pfannkuche
 */

import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'ProfilPersonalData',
    components: { ButtonContainer },
    data() {
        return {
            /**
             * @member {Object.<{firstName: String, lastName: String, email: String}>} - User data
             */
            user: {
                firstName: this.$auth.user.firstName,
                lastName: this.$auth.user.lastName,
                email: this.$auth.user.email,
            },
            /**
             * @member {Boolean} loading - Request status
             */
            loading: false,
        }
    },
    methods: {
        /**
         * @method updateUserData
         * @desc Calls api endpoint to change user data and handles response
         * @returns {Promise<void>}
         */
        async updateUserData() {
            try {
                await this.$api.updateUserData(this.user, this.$auth.getToken('keycloak'))
                this.$emit('success', 'Deine Benutzerdaten wurde erfolgreich geändert.')

                if (this.$auth.user.email !== this.user.email) {
                    this.$emit('loginDataChanged')
                } else {
                    await this.$auth.fetchUser()
                }
            } catch (err) {
                this.$emit('error', err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.')
            }
        },
        /**
         * @method onSubmit
         * @desc Validates the form, shows validation state and calls {@link component:ProfilPersonalData~updateUserData updateUserData} if the form is valid
         * @param {Object} event - Browser event which is fired on submitting the form
         */
        async onSubmit(event) {
            this.loading = true
            this.$emit('reset')
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
    },
}
</script>

<style scoped></style>
