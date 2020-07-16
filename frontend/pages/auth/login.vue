<template>
    <login-form :login-page="true" modal-id="modal-center" />
</template>

<script>
import LoginForm from '~/components/login/form'

export default {
    name: 'AuthLogin',
    components: { LoginForm },
    layout: 'blank',
    mounted() {
        this.$bvModal.show('modal-center')
    },
}
</script>

<style scoped></style>
