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
import ProfilChangePassword from '~/components/shop/account/profil/change-password'
import ProfilPersonalData from '~/components/shop/account/profil/personal-data'

export default {
    name: 'AccountProfil',
    components: { ProfilChangePassword, ProfilPersonalData },
    data() {
        return {
            needsNewLogin: false,
            success: '',
            errorData: '',
            errorPassword: '',
        }
    },
    methods: {
        async login() {
            await this.$auth.logout()
            this.$router.push('/auth/login')
        },
        resetStatus() {
            this.needsNewLogin = false
            this.success = ''
            this.errorData = ''
            this.errorPassword = ''
        },
        showErrorMessage(event, type) {
            if (type === 'data') {
                this.errorData = event
            } else if (type === 'password') {
                this.errorPassword = event
            }
        },
        showSuccessMessage(event) {
            this.success = event
        },
    },
}
</script>

<style scoped></style>
