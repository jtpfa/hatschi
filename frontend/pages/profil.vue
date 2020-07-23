<template>
    <b-container class="d-flex justify-content-center my-5">
        <div class="col-12 col-sm-10 col-md-6 col-xl-5">
            <profil-form />
        </div>
    </b-container>
</template>

<script>
import ProfilForm from '~/components/profil/form'

export default {
    name: 'Profil',
    components: { ProfilForm },
    middleware: ['auth'],
}
</script>

<style scoped></style>
