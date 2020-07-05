import $ from 'jquery'

/**
 * Checks if password and passwordConfirmation match and sets the html5 validation information.
 * Returns true when password and passwordConfirmation match.
 * @param password {Object}
 * @param passwordConfirmation {Object}
 * @returns {boolean}
 */
const isPasswordConfirmed = (password, passwordConfirmation) => {
    if (!password || !passwordConfirmation) {
        return false
    }

    if (password.value !== passwordConfirmation.value) {
        passwordConfirmation.setCustomValidity('Passwords do not match.')
        return false
    }

    passwordConfirmation.setCustomValidity('')
    return true
}

$(document).ready(() => {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = $(document).find('.needs-validation')
    // Loop over them and prevent submission
    Array.prototype.filter.call(forms, form => {
        // Look for password fields
        const password = $('form #password')[0]
        const passwordConfirmation = $('form #password-confirm')[0]

        // Extend the html5 form validation with a custom password confirmation validator if the password fields exist
        if (password && passwordConfirmation) {
            password.onchange = () => {
                isPasswordConfirmed(password, passwordConfirmation)
            }
            passwordConfirmation.onkeyup = () => {
                isPasswordConfirmed(password, passwordConfirmation)
            }

            form.addEventListener(
                'submit',
                event => {
                    if (!form.checkValidity() || !isPasswordConfirmed(password, passwordConfirmation)) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                },
                false
            )
        } else {
            form.addEventListener(
                'submit',
                event => {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                },
                false
            )
        }
    })
}, false)
