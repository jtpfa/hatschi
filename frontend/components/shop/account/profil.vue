<template>
    <div>
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
}
</script>

<style scoped></style>
