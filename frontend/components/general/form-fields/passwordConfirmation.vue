<template>
    <fieldset>
        <div class="mb-4" role="group">
            <label for="password">
                Passwort
                <span class="mandatory">*</span>
            </label>
            <b-form-input
                id="password"
                v-model="password"
                aria-describedby="input-live-help"
                autocomplete="new-password"
                pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[!§$%&/()=?|\\{}[\]+#;:.,@€_-])[A-Za-z\d!§$%&/()=?|\\{}[\]+#;:.,@€_-]{6,120}$"
                placeholder="Passwort"
                required
                trim
                type="password"
                @input="isPasswordConfirmed"
            />

            <b-form-text id="input-live-help" class="mt-0">
                <form-field-password-strength :password="password" />
            </b-form-text>
        </div>

        <div class="mb-4" role="group">
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
                <template v-if="!passwordConfirmationErrorMessage">Bitte bestätige das Passwort.</template>
                <template v-else>
                    {{ passwordConfirmationErrorMessage }}
                </template>
            </b-form-invalid-feedback>
        </div>
    </fieldset>
</template>
<script>
import FormFieldPasswordStrength from '~/components/general/form-fields/passwordStrength'

export default {
    name: 'FormFieldPasswordConfirmation',
    components: { FormFieldPasswordStrength },
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
                this.$refs.passwordConfirmation?.setCustomValidity('Passwords do not match.')
                return false
            }

            if (
                !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[!§$%&/()=?|\\{}[\]+#;:.,@€_-])[A-Za-z\d!§$%&/()=?|\\{}[\]+#;:.,@€_-]{6,120}$/.test(
                    this.passwordConfirmation
                )
            ) {
                this.passwordConfirmationErrorMessage =
                    'Wähle ein Passwort, das den Sicherheitsbestimmungen entspricht.'
                this.$refs.passwordConfirmation?.setCustomValidity('Password does not match criteria.')
                return false
            }

            this.$refs.passwordConfirmation?.setCustomValidity('')
            return true
        },
    },
}
</script>

<style lang="scss" scoped>
input#password ~ #input-live-help {
    display: none;
}

input#password:focus,
.was-validated input#password:invalid {
    ~ #input-live-help {
        display: block;
    }
}
</style>
