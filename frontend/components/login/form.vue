<template>
    <b-modal :id="modalId" centered footer-class="login-footer" lazy ok-only scrollable title="Login">
        <b-form ref="form" novalidate>
            <div class="mb-3" role="group">
                <label for="email">
                    E-Mail
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="email"
                    ref="email"
                    v-model="email"
                    aria-describedby="input-live-feedback"
                    autocomplete="email"
                    placeholder="E-Mail"
                    required
                    trim
                    type="email"
                ></b-form-input>

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib eine gültige E-Mail Adresse an.
                </b-form-invalid-feedback>
            </div>

            <div role="group">
                <label for="password">
                    Passwort
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="password"
                    v-model="password"
                    aria-describedby="input-live-feedback"
                    autocomplete="current-password"
                    placeholder="Passwort"
                    required
                    trim
                    type="password"
                ></b-form-input>

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib dein Passwort ein.
                </b-form-invalid-feedback>
            </div>
        </b-form>

        <template v-slot:modal-footer>
            <div class="w-100">
                <b-button class="float-right" type="submit" variant="primary" @click.prevent="onSubmit">Login</b-button>
            </div>
        </template>
    </b-modal>
</template>

<script>
export default {
    name: 'LoginForm',
    props: {
        modalId: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            email: '',
            password: '',
        }
    },
    methods: {
        onSubmit(event) {
            if (!this.$refs.form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
                this.$refs.form.classList.add('was-validated')
                return
            }
            this.$refs.form.classList.add('was-validated')
            this.$bvModal.hide(this.modalId)
        },
    },
}
</script>

<style lang="scss" scoped>
::v-deep .login-footer {
    border-top: none;
}
</style>
