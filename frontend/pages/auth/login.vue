<template>
    <login-form :login-page="true" />
</template>

<script>
/**
 * @component AuthLoginPage
 * @desc Login page
 * @author Jonas Pfannkuche
 */

import LoginForm from '~/components/general/login/form'

export default {
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
