<template>
    <main class="container d-flex justify-content-center my-5">
        <div class="col-12 col-sm-10 col-md-6 col-xl-5">
            <registration-form />
        </div>
    </main>
</template>

<script>
import RegistrationForm from '~/components/general/registration/form'

export default {
    components: { RegistrationForm },
    head() {
        return {
            title: `PC Masterrace – Registrierung`,
        }
    },
}
</script>

<style scoped></style>
