<template>
    <b-form @submit="onSubmit" validated>
        <b-form-row>
            <div class="col-md-6" role="group">
                <label for="firstname">Vorname<span class="mandatory">*</span></label>
                <b-form-input
                        id="firstname"
                        aria-describedby="input-live-feedback"
                        placeholder="Vorname"
                        autocomplete="given-name"
                        v-model="firstName"
                        required
                        trim
                ></b-form-input>

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib deinen Vornamen an.
                </b-form-invalid-feedback>
            </div>
            <div class="col-md-6" role="group">
                <label for="name">Nachname<span class="mandatory">*</span></label>
                <b-form-input
                        id="name"
                        aria-describedby="input-live-feedback"
                        placeholder="Nachname"
                        autocomplete="family-name"
                        v-model="lastName"
                        required
                        trim
                ></b-form-input>

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib deinen Nachnamen an.
                </b-form-invalid-feedback>
            </div>
        </b-form-row>

        <div role="group">
            <label for="email">E-Mail<span class="mandatory">*</span></label>
            <b-form-input
                    id="email"
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

        <b-form-row>
            <div class="col-md-6" role="group">
                <label for="password">Passwort<span class="mandatory">*</span></label>
                <b-form-input
                        @input="isPasswordConfirmed"
                        id="password"
                        aria-describedby="input-live-help input-live-feedback"
                        type="password"
                        placeholder="Passwort"
                        autocomplete="new-password"
                        pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[!§$%&/()=?|\\{}[\]+#;:.,@€_-])[A-Za-z\d!§$%&/()=?|\\{}[\]+#;:.,@€_-]{6,}$"
                        v-model="password"
                        required
                        trim
                ></b-form-input>

                <b-form-text id="input-live-help">
                    Das Passwort muss mindestens aus 6 Zeichen, bestehend aus Buchstaben, Zahlen und Sonderzeichen
                    (<kbd>!§$%&/()=?|{}[]+#;:.,@€_-</kbd>).
                </b-form-text>

                <b-form-invalid-feedback id="input-live-feedback">
                    Wähle ein Passwort, das den Sicherheitsbestimmungen entspricht.
                </b-form-invalid-feedback>
            </div>

            <div class="col-md-6" role="group">
                <label for="password-confirm">Passwort bestätigen<span class="mandatory">*</span></label>
                <b-form-input
                        @input="isPasswordConfirmed"
                        ref="passwordConfirmation"
                        id="password-confirm"
                        aria-describedby="input-live-feedback"
                        type="password"
                        placeholder="Passwort bestätigen"
                        autocomplete="new-password"
                        v-model="passwordConfirmation"
                        required
                        trim
                ></b-form-input>

                <b-form-invalid-feedback id="input-live-feedback">
                    <template v-if="!passwordConfirmationErrorMessage">
                        Bitte bestätige das Passwort.
                    </template>
                    <template v-else>
                        {{ passwordConfirmationErrorMessage }}
                    </template>
                </b-form-invalid-feedback>
            </div>
        </b-form-row>
        <b-button type="submit" variant="primary">Registrieren</b-button>
    </b-form>
</template>

<script>
  export default {
    name: 'RegistrationForm',
    data() {
      return {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        passwordConfirmation: '',
        passwordConfirmationErrorMessage: '',
      }
    },
    methods: {
      onSubmit(){
        this.isPasswordConfirmed()
      },
      isPasswordConfirmed() {
        if (this.password !== this.passwordConfirmation) {
          this.passwordConfirmationErrorMessage = 'Prüfe, ob deine Passwörter übereinstimmen.'
          this.$refs.passwordConfirmation.setCustomValidity('Passwords do not match.')
          return false
        }

        if (
          !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[!§$%&/()=?|\\{}[\]+#;:.,@€_-])[A-Za-z\d!§$%&/()=?|\\{}[\]+#;:.,@€_-]{6,}$/.test(
            this.passwordConfirmation
          )
        ) {
          this.passwordConfirmationErrorMessage = 'Wähle ein Passwort, das den Sicherheitsbestimmungen entspricht.'
          this.$refs.passwordConfirmation.setCustomValidity('Password does not match criteria.')
          return false
        }

        this.$refs.passwordConfirmation.setCustomValidity('')
        return true
      }
    }
  }
</script>

<style scoped>

</style>