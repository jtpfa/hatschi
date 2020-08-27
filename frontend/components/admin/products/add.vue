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

                    <b-form-invalid-feedback id="input-live-feedback">Beschreibung angeben.</b-form-invalid-feedback>
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

                    <b-form-invalid-feedback id="input-live-feedback">Details angeben.</b-form-invalid-feedback>
                </div>

                <div class="mb-4" role="group">
                    <label for="detailsa">
                        Details
                        <span class="mandatory">*</span>
                    </label>
                    <ckeditor
                        id="detailsa"
                        v-model.trim="details"
                        class="form-control"
                        :class="{ valid: details, invalid: !details }"
                        :config="editor.config"
                        :editor="editor.editor"
                        placeholder="Details"
                        tag-name="textarea"
                    ></ckeditor>

                    <b-form-invalid-feedback id="input-live-feedback">Details angeben.</b-form-invalid-feedback>
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

                    <b-form-invalid-feedback id="input-live-feedback">Preis angeben.</b-form-invalid-feedback>
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

                    <b-form-invalid-feedback id="input-live-feedback">Lagerbestand angeben.</b-form-invalid-feedback>
                </div>

                <form-field-file-upload ref="fileInput" />

                <b-alert class="mt-3" :show="error.length > 0" variant="danger">{{ error }}</b-alert>

                <button-container :loading="loading" text="Artikel hinzufügen" />
            </b-form>
        </client-only>
    </div>
</template>

<script>
import FormFieldFileUpload from '~/components/admin/form-fields/fileUpload'
// @todo export ckeditor to own component
/* eslint global-require: "off" */
import ButtonContainer from '~/components/general/layout/buttonContainer'

let ckeditor
let ClassicEditor = {}
if (process.client) {
    ClassicEditor = require('@ckeditor/ckeditor5-build-classic')
    ckeditor = require('@ckeditor/ckeditor5-vue').component
}

export default {
    name: 'ProductAdd',
    components: { FormFieldFileUpload, ButtonContainer, ckeditor },
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
                    this.$refs.fileInput.image,
                    this.$auth.getToken('keycloak')
                )
                this.success = 'Der Artikel wurde erfolgreich angelegt.'
                this.clearForm()
                window.scrollTo(0, 0)
            } catch (err) {
                this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
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
            this.$refs.fileInput.image = null
        },
    },
}
</script>

<style lang="scss" scoped>
::v-deep #detailsa ~ .ck-editor {
    .ck-toolbar {
        border-radius: $border-radius $border-radius 0 0;
    }

    .ck-content {
        border-radius: 0 0 $border-radius $border-radius;
        border-color: $gray-400;

        &.ck-focused,
        &:focus {
            color: $gray-700;
            background-color: $white;
            border-color: #9acffa;
            border-radius: 0.25rem;
            outline: 0;
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 0 0.2rem rgba(33, 150, 243, 0.25);
        }
    }
}

::v-deep .was-validated #detailsa ~ .ck-editor {
    .ck-toolbar .ck-toolbar__items {
        background-position: right calc(0.375em + 0.1875rem) center;
        margin-right: -0.25em;
        background-repeat: no-repeat;
        background-size: calc(0.75em + 0.525rem) calc(0.75em + 0.525rem);
    }
}

::v-deep .was-validated #detailsa.invalid ~ .ck-editor {
    .ck-toolbar {
        border-color: $danger;

        .ck-toolbar__items {
            background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' fill='none' stroke='%23f44336' viewBox='0 0 12 12'%3e%3ccircle cx='6' cy='6' r='4.5'/%3e%3cpath stroke-linejoin='round' d='M5.8 3.6h.4L6 6.5z'/%3e%3ccircle cx='6' cy='8.2' r='.6' fill='%23f44336' stroke='none'/%3e%3c/svg%3e");
        }
    }

    .ck-content {
        border-color: $danger;

        &.ck-focused,
        &:focus {
            box-shadow: 0 0 0 0.2rem rgba(244, 67, 54, 0.25);
        }
    }
}

::v-deep .was-validated #detailsa.valid ~ .ck-editor {
    .ck-toolbar {
        border-color: $success;

        .ck-toolbar__items {
            background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8' viewBox='0 0 8 8'%3e%3cpath fill='%2342ab7d' d='M2.3 6.73L.6 4.53c-.4-1.04.46-1.4 1.1-.8l1.1 1.4 3.4-3.8c.6-.63 1.6-.27 1.2.7l-4 4.6c-.43.5-.8.4-1.1.1z'/%3e%3c/svg%3e");
        }
    }

    .ck-content {
        border-color: $success;

        &.ck-focused,
        &:focus {
            box-shadow: 0 0 0 0.2rem rgba(66, 171, 125, 0.25);
        }
    }
}
</style>
