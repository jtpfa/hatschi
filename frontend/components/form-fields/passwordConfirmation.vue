<template>
    <b-form-row>
        <div class="col-md-6" role="group">
            <label for="password">
                Passwort
                <span class="mandatory">*</span>
            </label>
            <b-form-input
                id="password"
                v-model="password"
                aria-describedby="input-live-help input-live-feedback"
                autocomplete="new-password"
                pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[!§$%&/()=?|\\{}[\]+#;:.,@€_-])[A-Za-z\d!§$%&/()=?|\\{}[\]+#;:.,@€_-]{6,120}$"
                placeholder="Passwort"
                required
                trim
                type="password"
                @input="isPasswordConfirmed"
            />

            <b-form-text id="input-live-help">
                Das Passwort muss mindestens aus 6 Zeichen, bestehend aus Buchstaben, Zahlen und Sonderzeichen (
                <kbd>!§$%&/()=?|{}[]+#;:.,@€_-</kbd>
                ).
            </b-form-text>

            <b-form-invalid-feedback id="input-live-feedback">
                Wähle ein Passwort, das den Sicherheitsbestimmungen entspricht.
            </b-form-invalid-feedback>
        </div>

        <div class="col-md-6" role="group">
            <label for="password-confirm">
                Passwort bestätigen
                <span class="mandatory">*</span>
            </label>
            <b-form-input
                id="password-confirm"
                ref="passwordConfirmation"
                v-model="passwordConfirmation"
                aria-describedby="input-live-feedback"
                autocomplete="new-password"
                placeholder="Passwort bestätigen"
                required
                trim
                type="password"
                @input="isPasswordConfirmed"
            />

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
</template>

<script>
export default {
    name: 'FormFieldPasswordConfirmation',
    data() {
        return {
            password: '',
            passwordConfirmation: '',
            passwordConfirmationErrorMessage: '',
        }
    },
    methods: {
        isPasswordConfirmed() {
            if (this.password !== this.passwordConfirmation) {
                this.passwordConfirmationErrorMessage = 'Prüfe, ob deine Passwörter übereinstimmen.'
                this.$refs.passwordConfirmation.setCustomValidity('Passwords do not match.')
                return false
            }

            if (
                !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[!§$%&/()=?|\\{}[\]+#;:.,@€_-])[A-Za-z\d!§$%&/()=?|\\{}[\]+#;:.,@€_-]{6,120}$/.test(
                    this.passwordConfirmation
                )
            ) {
                this.passwordConfirmationErrorMessage =
                    'Wähle ein Passwort, das den Sicherheitsbestimmungen entspricht.'
                this.$refs.passwordConfirmation.setCustomValidity('Password does not match criteria.')
                return false
            }

            this.$refs.passwordConfirmation.setCustomValidity('')
            return true
        },
    },
}
</script>

<style scoped></style>
