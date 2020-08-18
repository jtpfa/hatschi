<template>
    <b-modal :id="modalId" centered hide-footer return-focus="null" scrollable title="Login">
        <login-form @register="$bvModal.hide(modalId)" @success="$bvModal.hide(modalId)" />
    </b-modal>
</template>

<script>
import LoginForm from '~/components/login/form'

export default {
    name: 'LoginModal',
    components: { LoginForm },
    props: {
        modalId: {
            type: String,
            default: '',
        },
        hasAccess: {
            type: Boolean,
            default: false,
        },
    },
}
</script>

<style scoped></style>
