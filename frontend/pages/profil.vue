<template>
    <main class="container d-flex justify-content-center my-5">
        <div class="px-0 col-sm-11 col-md-8 col-xl-6">
            <b-card class="w-100" no-body title="Card Title">
                <b-tabs card justified>
                    <b-tab title="Adressen">
                        <h2 class="my-3">Aktuell hinterlegte Adressen</h2>
                        <account-show-all-addresses />

                        <b-button v-b-toggle.new-address class="mt-4" variant="primary">Neue Adresse</b-button>

                        <b-collapse id="new-address">
                            <h2 class="mt-5 mb-3">Adresse anlegen</h2>
                            <account-add-address />
                        </b-collapse>
                    </b-tab>
                    <b-tab title="Persönliche Daten">
                        <account-profil />
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
    </main>
</template>

<script>
import AccountAddAddress from '~/components/shop/account/address/add'
import AccountProfil from '~/components/shop/account/profil'
import AccountShowAllAddresses from '~/components/shop/account/address/showAll'

export default {
    components: { AccountAddAddress, AccountShowAllAddresses, AccountProfil },
    middleware: ['auth'],
    head() {
        return {
            title: `${this.$route.name.replace(/^\w/, c => c.toUpperCase())} – PC Masterrace`,
        }
    },
}
</script>

<style scoped></style>
