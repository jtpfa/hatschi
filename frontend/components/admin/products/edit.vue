<template>
    <b-modal
        :id="modalId"
        centered
        :hide-header-close="loading"
        lazy
        :no-close-on-backdrop="loading"
        :no-close-on-esc="loading"
        no-enforce-focus
        scrollable
        title="Produktdaten bearbeiten"
        @ok.prevent="onSubmit"
    >
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

                <b-form-invalid-feedback id="input-live-feedback">Bitte Beschreibung angeben.</b-form-invalid-feedback>
            </div>

            <form-field-editor ref="editorDetails" :current-details="product.details" />

            <div class="mb-4" role="group">
                <label for="price">
                    Preis
                    <span class="mandatory">*</span>
                </label>
                <b-input-group id="price" prepend="Euro und Cent">
                    <b-form-input
                        v-model="priceEur"
                        aria-describedby="input-live-feedback"
                        max="999999"
                        min="0"
                        required
                        trim
                        type="number"
                    />
                    <b-input-group-text>,</b-input-group-text>
                    <b-form-input
                        v-model="priceCt"
                        aria-describedby="input-live-feedback"
                        max="99"
                        min="0"
                        required
                        trim
                        type="number"
                    />
                    <b-input-group-append is-text>€</b-input-group-append>
                </b-input-group>

                <b-form-invalid-feedback id="input-live-feedback">Preis angeben.</b-form-invalid-feedback>
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

                <b-form-invalid-feedback id="input-live-feedback">Bitte Bestand angeben.</b-form-invalid-feedback>
            </div>

            <form-field-file-upload ref="fileInput" :replace="true" />

            <b-alert class="my-3" :show="error.length > 0" variant="danger">{{ error }}</b-alert>
        </b-form>

        <template v-slot:modal-footer="{ ok, cancel }">
            <!-- @todo remove modal footer and insert buttons in form to submit with enter -->
            <!-- Emulate built in modal footer ok and cancel button actions -->
            <b-button :disabled="loading" size="sm" variant="danger" @click="cancel()">Schließen</b-button>
            <b-button :disabled="loading" size="sm" variant="success" @click="ok()">
                <b-spinner v-if="loading" small></b-spinner>
                Speichern
            </b-button>
        </template>
    </b-modal>
</template>

<script>
import FormFieldEditor from '~/components/admin/form-fields/editor'
import FormFieldFileUpload from '~/components/admin/form-fields/fileUpload'

export default {
    name: 'CustomerEdit',
    components: { FormFieldEditor, FormFieldFileUpload },
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
    computed: {
        priceEur: {
            get() {
                if (this.product.price) {
                    const price = this.product.price.toString()
                    let eur = price.slice(0, price?.length - 2)

                    if (eur.length === 0) {
                        eur = 0
                    }

                    return eur
                }

                return -1
            },
            set(eur) {
                this.product.price = eur + this.priceCt
            },
        },
        priceCt: {
            get() {
                if (this.product.price) {
                    let ct = this.product.price.toString().slice(-2)

                    if (ct.length === 1) {
                        ct = `${ct}0`
                    }

                    return ct
                }

                return -1
            },
            set(ct) {
                this.product.price = `${this.priceEur}${ct.length === 1 ? `0${ct}` : `${ct}`}`
            },
        },
    },
    methods: {
        async editProduct() {
            try {
                await this.$api.editProduct(
                    {
                        id: this.product.id,
                        name: this.product.name,
                        description: this.product.description,
                        details: this.$refs.editorDetails.details,
                        price: +this.product.price,
                        stock: +this.product.stock,
                    },
                    this.$refs.fileInput.image,
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
            if (!this.$refs.form.checkValidity() || !this.$refs.editorDetails.isValid()) {
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
