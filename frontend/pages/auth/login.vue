<template>
    <login-form :login-page="true" :modal-id="modalId" />
</template>

<script>
import LoginForm from '~/components/login/form'

export default {
    name: 'AuthLogin',
    components: { LoginForm },
    layout: 'blank',
    data() {
        return {
            modalId: 'modal-login',
        }
    },
    mounted() {
        this.$bvModal.show(this.modalId)
    },
}
</script>

<style scoped></style>
