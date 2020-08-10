<template>
    <b-container class="d-flex justify-content-center my-5">
        <div class="col-12 col-sm-10 col-md-6 col-xl-5">
            <registration-form />
        </div>
    </b-container>
</template>

<script>
import RegistrationForm from '~/components/registration/form'

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
