<template>
    <div>
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
                    placeholder="0"
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
    </div>
</template>

<script>
import ButtonContainer from '~/components/layout/buttonContainer'

export default {
    name: 'ProductAdd',
    components: { ButtonContainer },
    data() {
        return {
            name: '',
            description: '',
            details: '',
            priceEur: 0,
            priceCt: 0,
            stock: 0,
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
                        price: +(this.priceEur + this.priceCt),
                        stock: +this.stock,
                    },
                    this.$auth.getToken('keycloak')
                )
                this.success = 'Der Artikel wurde erfolgreich angelegt.'
            } catch (err) {
                this.error = 'Leider gab es ein Problem. Bitte versuch es später erneut.'
            }
        },
        onSubmit(event) {
            this.loading = true
            this.success = ''
            this.error = ''
            if (!this.$refs.form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            } else {
                this.addProduct()
            }
            this.loading = false
            this.$refs.form.classList.add('was-validated')
        },
    },
}
</script>

<style scoped></style>
