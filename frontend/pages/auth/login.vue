<template>
    <login-form :login-page="true" />
</template>

<script>
import LoginForm from '~/components/login/form'

export default {
    name: 'AuthLogin',
    components: { LoginForm },
    layout: 'blank',
}
</script>

<style scoped></style>
