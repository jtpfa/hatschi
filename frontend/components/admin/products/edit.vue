<template>
    <b-modal :id="modalId" centered lazy scrollable title="Produktdaten bearbeiten" @ok.prevent="onSubmit">
        <b-form ref="form" novalidate>
            <div class="mb-4" role="group">
                <label for="firstname">
                    Vorname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="firstname"
                    v-model="product.firstName"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Vorname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib einen Vornamen an.
                </b-form-invalid-feedback>
            </div>
            <div class="mb-4" role="group">
                <label for="name">
                    Nachname
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="name"
                    v-model="product.lastName"
                    aria-describedby="input-live-feedback"
                    pattern="^.{1,50}$"
                    placeholder="Nachname"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte gib einen Nachnamen an.
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
    name: 'CustomerEdit',
    props: {
        product: {
            type: Object,
            required: true,
        },
        modalId: {
            type: String,
            default: '',
        },
    },
    methods: {
        onSubmit() {},
    },
}
</script>

<style scoped></style>
