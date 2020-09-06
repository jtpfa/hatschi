<template>
    <b-modal :id="modalId" centered hide-footer scrollable title="Nutzerdaten bearbeiten">
        <b-form ref="form" novalidate @submit.prevent="onSubmit">
            <div class="mb-4" role="group">
                <label for="email">
                    E-Mail
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="email"
                    v-model="username"
                    aria-describedby="input-live-feedback"
                    pattern="^.{4,100}$"
                    placeholder="E-Mail"
                    required
                    trim
                    type="email"
                />

                <b-form-invalid-feedback id="input-live-feedback">Bitte E-Mail angeben.</b-form-invalid-feedback>
            </div>

            <div class="mb-4" role="group">
                <label for="firstname">
                    Vorname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="firstname"
                    v-model="user.firstName"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Vorname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">Bitte Vornamen angeben.</b-form-invalid-feedback>
            </div>

            <div class="mb-4" role="group">
                <label for="name">
                    Nachname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="name"
                    v-model="user.lastName"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Nachname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">Bitte Nachnamen angeben.</b-form-invalid-feedback>
            </div>

            <div v-if="isAdmin" class="mb-4" role="group">
                <label for="name">
                    Rolle
                    <span class="mandatory">*</span>
                </label>
                <b-form-select v-model="selectedRole" :options="roleOptions" required />

                <b-form-invalid-feedback id="input-live-feedback">Bitte Rolle angeben.</b-form-invalid-feedback>
            </div>

            <b-alert class="mt-3" :show="error.length > 0" variant="danger" v-html="error" />

            <button-container :loading="loading" text="Daten speichern" />
        </b-form>
    </b-modal>
</template>

<script>
/**
 * @component UserEdit
 * @desc Form to edit an existing user inside of a modal. Modals er rendered by default on client side.
 * @lifecycle mounted - Check if user has admin privileges.
 * @author Jonas Pfannkuche
 */

import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'UserEdit',
    components: { ButtonContainer },
    props: {
        /**
         * @vprop {Object} user - User who should be edited
         */
        user: {
            type: Object,
            required: true,
        },
        /**
         * @vprop {String} modalId - Id of the modal to identifie it in root scope
         */
        modalId: {
            type: String,
            default: '',
        },
        /**
         * @vprop {('customer'|'employee'|'admin'|'order')} type - The allowed roles of an user
         */
        role: {
            type: String,
            required: true,
            validator(type) {
                // The value must match one of these strings
                return ['customer', 'employee', 'admin'].indexOf(type) !== -1
            },
        },
    },
    data() {
        return {
            isAdmin: false,
            newEmail: this.user.email,
            newRole: this.role,
            roleOptions: [
                { text: 'Kunde', value: 'customer' },
                { text: 'Mitarbeiter', value: 'employee' },
                { text: 'Admin', value: 'admin' },
            ],
            error: '',
            loading: false,
        }
    },
    computed: {
        /**
         * @computed {String} username - Synchronized username input field value
         */
        username: {
            get() {
                // If new email exists use it otherwise use the email from the user
                return this.newEmail ? this.newEmail : this.user.email
            },
            set(newEmail) {
                this.newEmail = newEmail
            },
        },
        /**
         * @computed {String} selectedRole - Synchronized role select field value
         */
        selectedRole: {
            get() {
                // If new role exists use it otherwise use the role from the user
                return this.newRole ? this.newRole : this.role
            },
            set(newRole) {
                this.newRole = newRole
            },
        },
    },
    mounted() {
        this.isAdmin = this.$auth.$state.roles?.includes('admin')
    },
    methods: {
        /**
         * @method editUser
         * @desc Calls api endpoint to edit customer as an employee or all user types as an admin and handles response
         * @returns {Promise<void>}
         */
        async editUser() {
            try {
                if (!this.isAdmin) {
                    await this.$api.editCustomer(
                        {
                            firstName: this.user.firstName,
                            lastName: this.user.lastName,
                            email: this.username,
                        },
                        this.user.email,
                        this.$auth.getToken('keycloak')
                    )
                } else {
                    await this.$api.editUser(
                        {
                            firstName: this.user.firstName,
                            lastName: this.user.lastName,
                            email: this.username,
                            customerRole: this.selectedRole.toLocaleUpperCase(),
                        },
                        this.user.email,
                        this.role,
                        this.$auth.getToken('keycloak')
                    )
                }

                this.newRole = ''
                this.$root.$emit('bv::hide::modal', this.modalId)
                this.$router.app.refresh()
            } catch (err) {
                this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
            }
        },
        /**
         * @method onSubmit
         * @desc Validates the form, shows validation state and calls {@link component:UserEdit~editUser editUser} if the form is valid
         * @param {Object} event - Browser event which is fired on submitting the form
         */
        async onSubmit(event) {
            this.loading = true
            this.error = ''
            if (!this.$refs.form.checkValidity()) {
                this.$refs.form.classList.add('was-validated')
                event.preventDefault()
            } else {
                this.$refs.form.classList.add('was-validated')
                await this.editUser()
            }
            this.loading = false
        },
    },
}
</script>

<style scoped></style>
