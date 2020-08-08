<template>
    <div>
        <client-only>
            <b-alert class="my-3" :show="success.length > 0" variant="success">{{ success }}</b-alert>

            <b-form ref="form" novalidate @submit.prevent="onSubmit">
                <div class="mb-4" role="group">
                    <label for="name">
                        Artikelbezeichnung
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        id="name"
                        v-model="name"
                        aria-describedby="input-live-feedback"
                        pattern="^.{4,255}$"
                        placeholder="Artikelbezeichnung"
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Artikelbezeichnung angeben.
                    </b-form-invalid-feedback>
                </div>
                <div class="mb-4" role="group">
                    <label for="description">
                        Beschreibung
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        id="name"
                        v-model="description"
                        aria-describedby="input-live-feedback"
                        pattern="^.{4,4096}$"
                        placeholder="Beschreibung"
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Beschreibung angeben.
                    </b-form-invalid-feedback>
                </div>

                <div class="mb-4" role="group">
                    <label for="details">
                        Details
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        id="details"
                        v-model="details"
                        aria-describedby="input-live-feedback"
                        pattern="^.{4,32768}$"
                        placeholder="Details"
                        required
                        trim
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Details angeben.
                    </b-form-invalid-feedback>
                </div>

                <div class="mb-4" role="group">
                    <label for="de">
                        Details
                        <span class="mandatory">*</span>
                    </label>
                    <ckeditor
                        id="det"
                        v-model.trim="details"
                        class="form-control"
                        :config="editor.config"
                        :editor="editor.editor"
                        placeholder="Details"
                        tag-name="textarea"
                    ></ckeditor>

                    <b-form-invalid-feedback id="input-live-feedback">
                        Details angeben.
                    </b-form-invalid-feedback>
                </div>

                <div class="mb-4" role="group">
                    <label for="price">
                        Preis
                        <span class="mandatory">*</span>
                    </label>
                    <b-input-group id="price" prepend="Euro und Cent">
                        <b-form-input
                            v-model="priceEur"
                            aria-describedby="input-live-feedback"
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

                    <b-form-invalid-feedback id="input-live-feedback">
                        Preis angeben.
                    </b-form-invalid-feedback>
                </div>

                <div class="mb-4" role="group">
                    <label for="stock">
                        Lagerbestand
                        <span class="mandatory">*</span>
                    </label>
                    <b-form-input
                        id="stock"
                        v-model="stock"
                        aria-describedby="input-live-feedback"
                        placeholder="Menge"
                        required
                        trim
                        type="number"
                    />

                    <b-form-invalid-feedback id="input-live-feedback">
                        Lagerbestand angeben.
                    </b-form-invalid-feedback>
                </div>

                <b-alert class="mt-3" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

                <button-container :loading="loading" text="Artikel hinzufügen" />
            </b-form>
        </client-only>
    </div>
</template>

<script>
/* eslint global-require: "off" */
import ClientOnly from 'vue-client-only'
import ButtonContainer from '~/components/layout/buttonContainer'

let ckeditor
let ClassicEditor = {}
if (process.client) {
    ClassicEditor = require('@ckeditor/ckeditor5-build-classic')
    ckeditor = require('@ckeditor/ckeditor5-vue').component
}

export default {
    name: 'ProductAdd',
    components: { ButtonContainer, ClientOnly, ckeditor },
    data() {
        return {
            name: '',
            description: '',
            details: '',
            priceEur: 0,
            priceCt: 0,
            stock: 0,
            editor: {
                editor: ClassicEditor,
                config: {
                    editor: ClassicEditor,
                    toolbar: {
                        items: [
                            'bold',
                            'italic',
                            '|',
                            'link',
                            '|',
                            'bulletedList',
                            'numberedList',
                            '|',
                            'undo',
                            'redo',
                        ],
                        viewportTopOffset: 100,
                    },
                },
            },
            success: '',
            error: '',
            loading: false,
        }
    },
    methods: {
        async addProduct() {
            try {
                await this.$api.addProduct(
                    {
                        name: this.name,
                        description: this.description,
                        details: this.details,
                        price: +(this.priceEur + (this.priceCt <= 9 ? `0${this.priceCt}` : this.priceCt)),
                        stock: +this.stock,
                    },
                    this.$auth.getToken('keycloak')
                )
                this.success = 'Der Artikel wurde erfolgreich angelegt.'
                this.clearForm()
            } catch (err) {
                this.error = 'Leider gab es ein Problem. Bitte später erneut versuchen.'
            }
        },
        async onSubmit(event) {
            this.loading = true
            this.success = ''
            this.error = ''
            if (!this.$refs.form.checkValidity()) {
                this.$refs.form.classList.add('was-validated')
                event.preventDefault()
                event.stopPropagation()
            } else {
                this.$refs.form.classList.add('was-validated')
                await this.addProduct()
            }
            this.loading = false
        },
        clearForm() {
            this.$refs.form.classList.remove('was-validated')
            this.name = ''
            this.description = ''
            this.details = ''
            this.priceEur = 0
            this.priceCt = 0
            this.stock = 0
        },
    },
}
</script>

<style lang="scss" scoped>
::v-deep .was-validated :invalid ~ .ck-editor {
    .ck-editor__top {
        background-position: right calc(0.375em + 0.1875rem) center;

        border-color: #f44336;
        padding-right: calc(1.5em + 0.75rem);
        background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' fill='none' stroke='%23f44336' viewBox='0 0 12 12'%3e%3ccircle cx='6' cy='6' r='4.5'/%3e%3cpath stroke-linejoin='round' d='M5.8 3.6h.4L6 6.5z'/%3e%3ccircle cx='6' cy='8.2' r='.6' fill='%23f44336' stroke='none'/%3e%3c/svg%3e");
        background-repeat: no-repeat;
        background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
    }
}
</style>
