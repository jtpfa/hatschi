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
import FormFieldEditor from '~/components/admin/form-fields/editor'
import FormFieldFileUpload from '~/components/admin/form-fields/fileUpload'
import ButtonContainer from '~/components/general/layout/buttonContainer'

export default {
    name: 'ProductAdd',
    components: { FormFieldEditor, FormFieldFileUpload, ButtonContainer },
    data() {
        return {
            name: '',
            description: '',
            priceEur: 0,
            priceCt: 0,
            stock: 0,
            success: '',
            error: '',
            loading: false,
        }
    },
    methods: {
        checkEuroRange(value) {
            if (+value < 0) {
                return '0'
            }
            if (+value > 999999) {
                return '999999'
            }
            return value
        },
        checkCtRange(value) {
            if (+value < 0) {
                return '0'
            }
            if (+value > 99) {
                return '99'
            }
            return value
        },
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
