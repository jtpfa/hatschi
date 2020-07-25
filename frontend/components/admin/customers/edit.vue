<template>
    <b-modal :id="modalId" centered lazy scrollable title="Kundendaten bearbeiten" @ok.prevent="onSubmit">
        <b-form ref="form" novalidate>
            <div class="mb-4" role="group">
                <label for="firstname">
                    Vorname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="firstname"
                    v-model="customer.firstName"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Vorname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte Vornamen angeben.
                </b-form-invalid-feedback>
            </div>
            <div class="mb-4" role="group">
                <label for="name">
                    Nachname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="name"
                    v-model="customer.lastName"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Nachname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte Nachnamen angeben.
                </b-form-invalid-feedback>
            </div>
        </b-form>

        <template v-slot:modal-footer="{ ok, cancel }">
            <!-- Emulate built in modal footer ok and cancel button actions -->
            <b-button size="sm" variant="danger" @click="cancel()">
                Schließen
            </b-button>
            <b-button size="sm" variant="success" @click="ok()">
                Speichern
            </b-button>
        </template>
    </b-modal>
</template>

<script>
export default {
    name: 'ProductEdit',
    props: {
        customer: {
            type: Object,
            required: true,
        },
        modalId: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            loading: false,
        }
    },
    methods: {
        onSubmit(event) {
            this.loading = true
            if (!this.$refs.form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }
            this.loading = false
            this.$refs.form.classList.add('was-validated')
        },
    },
}
</script>

<style scoped></style>
