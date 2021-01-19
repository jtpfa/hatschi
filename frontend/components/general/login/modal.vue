<template>
    <b-modal :id="modalId" centered hide-footer return-focus="null" scrollable title="Login">
        <login-form @register="$bvModal.hide(modalId)" @success="$bvModal.hide(modalId)" />
    </b-modal>
</template>

<script>
/**
 * @component LoginModal
 * @desc Modal for login form
 * @author Jonas Pfannkuche
 */

import LoginForm from '~/components/general/login/form'

export default {
    name: 'LoginModal',
    components: { LoginForm },
    props: {
        /**
         * @vprop {String} modalId - Id of the modal to identify it in root scope
         */
        modalId: {
            type: String,
            default: '',
        },
    },
}
</script>

<style scoped></style>
