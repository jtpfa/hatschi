<template>
    <div>
        <b-alert class="my-3" :show="success.length > 0" variant="success">
            {{ success }}
            <template v-if="needsNewLogin">
                Bitte mit den neuen Anmeldedaten
                <b-link class="alert-link>" @click="login">einloggen.</b-link>
            </template>
        </b-alert>

        <template v-if="!needsNewLogin">
            <h2>Meine Daten</h2>
            <b-alert class="mt-3" :show="errorData.length > 0" variant="danger" v-html="errorData" />
            <profil-personal-data
                @error="showErrorMessage($event, 'data')"
                @loginDataChanged="needsNewLogin = true"
                @reset="resetStatus"
                @success="showSuccessMessage($event)"
            />

            <h2 class="mt-5">Passwort ändern</h2>
            <b-alert class="mt-3" :show="errorPassword.length > 0" variant="danger" v-html="errorPassword" />
            <profil-change-password
                @error="showErrorMessage($event, 'password')"
                @loginDataChanged="needsNewLogin = true"
                @reset="resetStatus"
                @success="showSuccessMessage($event)"
            />
        </template>
    </div>
</template>

<script>
/**
 * @component AccountProfil
 * @desc General user related information (user data overview and editing and password editing)
 * @author Jonas Pfannkuche
 */

import ProfilChangePassword from '~/components/shop/account/profil/change-password'
import ProfilPersonalData from '~/components/shop/account/profil/personal-data'

export default {
    name: 'AccountProfil',
    components: { ProfilChangePassword, ProfilPersonalData },
    data() {
        return {
            /**
             * @member {Boolean} needsNewLogin - If the email or password have changed, a new log in is required
             */
            needsNewLogin: false,
            /**
             * @member {String} success - General succes message
             */
            success: '',
            /**
             * @member {String} errorData - Error message related to user data editing
             */
            errorData: '',
            /**
             * @member {String} errorPassword - Error message related to password change
             */
            errorPassword: '',
        }
    },
    methods: {
        /**
         * @method login
         * @desc Logs the user out and redirects to login page
         * @returns {Promise<void>}
         */
        async login() {
            await this.$auth.logout()
            this.$router.push('/auth/login')
        },
        /**
         * @method resetStatus
         * @desc Resets all component data, e. g. messages
         */
        resetStatus() {
            this.needsNewLogin = false
            this.success = ''
            this.errorData = ''
            this.errorPassword = ''
        },
        /**
         * @method showErrorMessage
         * @desc Shows an error message depending on the error type
         * @param {String} event - Error message
         * @param {'data'|'password'} type - Which data changing triggered the error?
         */
        showErrorMessage(event, type) {
            if (type === 'data') {
                this.errorData = event
            } else if (type === 'password') {
                this.errorPassword = event
            }
        },
        /**
         * @method showSuccessMessage
         * @desc Shows a success message
         * @param {String} event - Success message
         */
        showSuccessMessage(event) {
            this.success = event
        },
    },
}
</script>

<style scoped></style>
