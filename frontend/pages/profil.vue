<template>
    <b-container class="d-flex justify-content-center my-5">
        <div class="col-12 col-sm-10 col-md-6 col-xl-5">
            <profil-form />

            <h2>Bestellungen</h2>

            <all-orders />
        </div>
    </b-container>
</template>

<script>
import AllOrders from '~/components/shop/order'
import ProfilForm from '~/components/shop/profil/form'

export default {
    name: 'Profil',
    components: { AllOrders, ProfilForm },
    middleware: ['auth'],
    head() {
        return {
            title: `${this.$route.name.replace(/^\w/, c => c.toUpperCase())} – PC Masterrace`,
        }
    },
}
</script>

<style scoped></style>
