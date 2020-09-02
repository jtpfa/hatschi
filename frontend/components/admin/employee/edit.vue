<template>
    <b-modal :id="modalId" centered hide-footer scrollable title="Kundendaten bearbeiten">
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
                    v-model="employee.firstName"
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
                    v-model="employee.lastName"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Nachname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">Bitte Nachnamen angeben.</b-form-invalid-feedback>
            </div>

            <div class="mb-4" role="group">
                <label for="name">
                    Rolle
                    <span class="mandatory">*</span>
                </label>
                <b-form-select v-model="selectedRole" :options="roleOptions" required />

                <b-form-invalid-feedback id="input-live-feedback">Bitte Rolle angeben.</b-form-invalid-feedback>
            </div>

            <b-alert class="mt-3" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

            <button-container :loading="loading" text="Daten speichern" />
        </b-form>
    </b-modal>
</template>

<script>
import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'EmployeeEdit',
    components: { ButtonContainer },
    props: {
        employee: {
            type: Object,
            required: true,
        },
        modalId: {
            type: String,
            default: '',
        },
        role: {
            type: String,
            required: true,
            validator(type) {
                // The value must match one of these strings
                return ['employee', 'admin'].indexOf(type) !== -1
            },
        },
    },
    data() {
        return {
            newEmail: this.employee.email,
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
        username: {
            get() {
                return this.newEmail ? this.newEmail : this.employee.email
            },
            set(newEmail) {
                this.newEmail = newEmail
            },
        },
        selectedRole: {
            get() {
                return this.newRole ? this.newRole : this.role
            },
            set(newRole) {
                this.newRole = newRole
            },
        },
    },
    methods: {
        async editEmployee() {
            try {
                await this.$api.editEmployee(
                    {
                        email: this.username,
                        firstName: this.employee.firstName,
                        lastName: this.employee.lastName,
                        customerRole: this.selectedRole.toLocaleUpperCase(),
                    },
                    this.employee.email,
                    this.role,
                    this.$auth.getToken('keycloak')
                )

                this.$root.$emit('bv::hide::modal', this.modalId)
                this.$router.app.refresh()
            } catch (err) {
                this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
            }
        },
        async onSubmit(event) {
            this.loading = true
            this.error = ''
            if (!this.$refs.form.checkValidity()) {
                this.$refs.form.classList.add('was-validated')
                event.preventDefault()
            } else {
                this.$refs.form.classList.add('was-validated')
                await this.editEmployee()
            }
            this.loading = false
        },
    },
}
</script>

<style scoped></style>
