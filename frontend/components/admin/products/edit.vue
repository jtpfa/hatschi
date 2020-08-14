<template>
    <b-modal :id="modalId" centered lazy scrollable title="Produktdaten bearbeiten" @ok.prevent="onSubmit">
        <b-alert :show="error.length > 0" variant="danger">{{ error }}</b-alert>
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
                    pattern="^.{4,255}$"
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
                    pattern="^.{4,4096}$"
                    placeholder="Beschreibung"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte Beschreibung angeben.
                </b-form-invalid-feedback>
            </div>
            <div class="mb-4" role="group">
                <label for="details">
                    Details
                    <span class="mandatory">*</span>
                </label>
                <b-form-input
                    id="details"
                    v-model="product.details"
                    aria-describedby="input-live-feedback"
                    pattern="^.{4,32768}$"
                    placeholder="Details"
                    required
                    trim
                />

                <b-form-invalid-feedback id="input-live-feedback">
                    Bitte Details angeben.
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

                <!-- @todo insert file upload and ckeditor; add preview of current image if available-->
            </div>
        </b-form>

        <template v-slot:modal-footer="{ ok, cancel }">
            <!-- @todo remove modal footer and insert buttons in form to submit with enter -->
            <!-- Emulate built in modal footer ok and cancel button actions -->
            <b-button size="sm" variant="danger" @click="cancel()">
                Schließen
            </b-button>
            <b-button :disabled="loading" size="sm" variant="success" @click="ok()">
                <b-spinner v-if="loading" small></b-spinner>
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
    data() {
        return {
            error: '',
            loading: false,
        }
    },
    methods: {
        async editProduct() {
            try {
                await this.$api.editProduct(
                    {
                        id: this.product.id,
                        name: this.product.name,
                        description: this.product.description,
                        details: this.product.details,
                        price: +this.product.price,
                        stock: +this.product.stock,
                    },
                    this.$auth.getToken('keycloak')
                )

                this.$root.$emit('bv::hide::modal', this.modalId)
                this.$router.app.refresh()
            } catch (e) {
                this.error = 'Leider gab es ein Problem. Bitte später erneut versuchen.'
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
                await this.editProduct()
            }
            this.loading = false
        },
    },
}
</script>

<style scoped></style>
