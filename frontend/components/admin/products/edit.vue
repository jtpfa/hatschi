<template>
    <b-modal :id="modalId" centered lazy scrollable title="Produktdaten bearbeiten" @ok.prevent="onSubmit">
        <b-form ref="form" novalidate>
            <div class="mb-4" role="group">
                <label for="name">
                    Artikelbezeichnung
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="name"
                    v-model="product.name"
                    aria-describedby="input-live-feedback"
                    placeholder="Artikelbezeichnung"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte Artikelbezeichnung angeben.
                </b-form-invalid-feedback>
            </div>
            <div class="mb-4" role="group">
                <label for="description">
                    Beschreibung
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="description"
                    v-model="product.description"
                    aria-describedby="input-live-feedback"
                    placeholder="Beschreibung"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte Beschreibung angeben.
                </b-form-invalid-feedback>
            </div>
            <div class="mb-4" role="group">
                <label for="price">
                    Preis
                    <span class="mandatory">*</span>
                </label>
                <b-input-group id="price" append="ct">
                    <b-form-input
                        v-model="product.price"
                        aria-describedby="input-live-feedback"
                        min="0"
                        placeholder="Preis"
                        required
                        trim
                        type="number"
                    />
                </b-input-group>

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte Preis angeben.
                </b-form-invalid-feedback>
            </div>
            <div class="mb-4" role="group">
                <label for="stock">
                    Lagerbestand
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="stock"
                    v-model="product.stock"
                    aria-describedby="input-live-feedback"
                    min="0"
                    placeholder="Menge"
                    required
                    trim
                    type="number"
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte Bestand angeben.
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
        async editProduct() {
            try {
                await this.$api.editProduct(this.product, this.$auth.getToken('keycloak'))
            } catch (e) {
                // @todo error handling
            }
        },
        onSubmit(event) {
            if (!this.$refs.form.checkValidity()) {
                event.preventDefault()
            } else {
                this.editProduct()
            }
            this.$refs.form.classList.add('was-validated')
        },
    },
}
</script>

<style scoped></style>
