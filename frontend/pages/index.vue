<template>
    <b-container>
        <registration-form />
        <h1>{{ status }}</h1>
    </b-container>
</template>

<script>
import RegistrationForm from '../components/registration/form'

export default {
    components: {
        RegistrationForm,
    },
    computed: {
        status() {
            return this.$auth.user
        },
    },
}
</script>

<style></style>
