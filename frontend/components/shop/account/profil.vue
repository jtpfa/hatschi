<template>
    <div>
        <b-alert class="my-3" :show="success.length > 0" variant="success">
            {{ success }}
            <template v-if="needsNewLogin">
                Bitte mit den neuen Anmeldedaten
                <b-link class="alert-link>" @click="login">einloggen.</b-link>
            </template>
        </b-alert>

        <h2>Meine Daten</h2>
        <profil-personal-data />

        <h2 class="mt-5">Passwort ändern</h2>
        <profil-change-password />
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
            needNewLogin: false,
            success: '',
            error: '',
        }
    },
    methods: {
        async login() {
            await this.$auth.logout()
            this.$router.push('/auth/login')
        },
    },
}
</script>

<style scoped></style>
