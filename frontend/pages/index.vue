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
            return this.$auth.loggedIn ? this.$auth.user : 'No user logged in'
        },
    },
}
</script>

<style></style>
