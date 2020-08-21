<template>
    <login-form :login-page="true" />
</template>

<script>
import LoginForm from '~/components/general/login/form'

export default {
    name: 'AuthLogin',
    components: { LoginForm },
    layout: 'blank',
    head() {
        return {
            title: 'PC Masterrace – Login',
        }
    },
}
</script>

<style scoped></style>
