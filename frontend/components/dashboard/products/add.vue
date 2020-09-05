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

                <form-field-editor ref="editorDetails" />

                <div class="mb-4" role="group">
                    <label for="price">
                        Preis
                        <span class="mandatory">*</span>
                    </label>
                    <b-input-group id="price" prepend="Euro und Cent">
                        <b-form-input
                            v-model="priceEur"
                            aria-describedby="input-live-feedback"
                            :formatter="checkEuroRange"
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
                            :formatter="checkCtRange"
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
                        min="0"
                        placeholder="Menge"
                        required
                        trim
                        type="number"
                    />

                    <b-form-invalid-feedback id="input-live-feedback">Lagerbestand angeben.</b-form-invalid-feedback>
                </div>

                <form-field-file-upload ref="fileInput" />

                <b-alert class="mt-3" :show="error.length > 0" variant="danger" v-html="error" />

                <button-container :loading="loading" text="Artikel hinzufügen" />
            </b-form>
        </client-only>
    </div>
</template>

<script>
/**
 * @component ProductAdd
 * @desc Form to add a new product. It's only rendered on client side because of the ckeditor
 * @author Jonas Pfannkuche
 */

import FormFieldEditor from '~/components/dashboard/form-fields/editor'
import FormFieldFileUpload from '~/components/dashboard/form-fields/fileUpload'
import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'ProductAdd',
    components: { FormFieldEditor, FormFieldFileUpload, ButtonContainer },
    data() {
        return {
            /**
             * @member {String} name - Product name
             */
            name: '',
            /**
             * @member {String} description - Product description
             */
            description: '',
            /**
             * @member {Number} priceEur - Euros of product price
             */
            priceEur: 0,
            /**
             * @member {Number} priceCt - Cents of product price
             */
            priceCt: 0,
            /**
             * @member {Number} stock - Product stock
             */
            stock: 0,
            /**
             * @member {String} success - General success message
             */
            success: '',
            /**
             * @member {String} error - General error message
             */
            error: '',
            /**
             * @member {Boolean} loading - Show request status
             */
            loading: false,
        }
    },
    methods: {
        /**
         * @method checkEuroRange
         * @desc Checks if entered euro value are between 0 and 999999
         * @param {String} value - Current euro value of input field
         * @returns {String}
         */
        checkEuroRange(value) {
            if (+value < 0) {
                return '0'
            }
            if (+value > 999999) {
                return '999999'
            }
            return value
        },
        /**
         * @method checkCtRange
         * @desc Checks if entered cent value is between 0 and 99
         * @param {String} value - Current cent value of input field
         * @returns {String}
         */
        checkCtRange(value) {
            if (+value < 0) {
                return '0'
            }
            if (+value > 99) {
                return '99'
            }
            return value
        },
        /**
         * @method addProduct
         * @desc Calls api endpoint to add product and handles response
         * @returns {Promise<void>}
         */
        async addProduct() {
            try {
                await this.$api.addProduct(
                    {
                        name: this.name,
                        description: this.description,
                        details: this.$refs.editorDetails.details,
                        price: +(
                            this.priceEur +
                            (+this.priceCt !== 0 && +this.priceCt <= 9 ? `0${+this.priceCt}` : +this.priceCt)
                        ),
                        stock: +this.stock,
                    },
                    this.$refs.fileInput.image,
                    this.$auth.getToken('keycloak')
                )
                this.success = 'Der Artikel wurde erfolgreich angelegt.'
                this.clearForm()
                window.scrollTo(0, 0)
                this.$router.app.refresh()
            } catch (err) {
                this.error = err.message || 'Leider gab es ein Problem. Bitte später erneut versuchen.'
            }
        },
        /**
         * @method onSubmit
         * @desc Validates the form, shows validation state and calls {@link component:ProductAdd~addProduct addProduct} if the form is valid
         * @param {Object} event - Browser event which is fired on submitting the form
         */
        async onSubmit(event) {
            this.loading = true
            this.success = ''
            this.error = ''
            if (!this.$refs.form.checkValidity() || !this.$refs.editorDetails.isValid()) {
                this.$refs.form.classList.add('was-validated')
                event.preventDefault()
                event.stopPropagation()
            } else {
                this.$refs.form.classList.add('was-validated')
                await this.addProduct()
            }
            this.loading = false
        },
        /**
         * @method clearForm
         * @desc Clears all input fields and removes validation state of form
         */
        clearForm() {
            this.$refs.form.classList.remove('was-validated')
            this.name = ''
            this.description = ''
            this.priceEur = 0
            this.priceCt = 0
            this.stock = 0
            this.$refs.editorDetails.details = ''
            this.$refs.fileInput.image = null
        },
    },
}
</script>

<style scoped></style>
