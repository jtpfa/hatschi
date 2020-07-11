<template>
    <b-modal footer-class="login-footer" :id="this.modalId" centered title="Login" ok-only lazy scrollable>
        <b-form novalidate ref="form">
            <div class="mb-3" role="group">
                <label for="email">E-Mail<span class="mandatory">*</span></label>
                <b-form-input
                        id="email"
                        ref="email"
                        aria-describedby="input-live-feedback"
                        type="email"
                        placeholder="E-Mail"
                        autocomplete="email"
                        v-model="email"
                        required
                        trim
                ></b-form-input>

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib eine gültige E-Mail Adresse an.
                </b-form-invalid-feedback>
            </div>

            <div role="group">
                <label for="password">Passwort<span class="mandatory">*</span></label>
                <b-form-input
                        id="password"
                        aria-describedby="input-live-feedback"
                        type="password"
                        placeholder="Passwort"
                        autocomplete="current-password"
                        v-model="password"
                        required
                        trim
                ></b-form-input>

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib dein Passwort ein.
                </b-form-invalid-feedback>
            </div>
        </b-form>

        <template v-slot:modal-footer>
            <div class="w-100">
                <b-button class="float-right" type="submit" @click.prevent="onSubmit" variant="primary">Login</b-button>
            </div>
        </template>
    </b-modal>
</template>

<script>
  export default {
    name: 'loginForm',
    data () {
      return {
        email: '',
        password: '',
      }
    },
    props: {
      modalId: ''
    },
    methods: {
      onSubmit (event) {
        if (!this.$refs.form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
          this.$refs.form.classList.add('was-validated')
          return
        }
        this.$refs.form.classList.add('was-validated')
        this.$bvModal.hide(this.modalId)
        console.log(this.email, this.password)
      },
    }
  }
</script>

<style lang="scss" scoped>
    ::v-deep .login-footer {
        border-top: none;
    }
</style>