<template>
    <div class="mb-5" role="group">
        <label for="image">
            {{ replace ? 'Bild (aktuelles Bild wird ersetzt)' : 'Bild' }}
        </label>
        <b-form-file
            id="image"
            v-model="image"
            accept="image/jpg, image/png"
            aria-describedby="file-upload-help"
            browse-text="Datei auswählen"
            drop-placeholder="Datei hierhin ziehen..."
            :state="acceptedFile"
        />

        <b-form-text id="file-upload-help" class="mt-2">
            Zulässige Bildformate: jpg, png
            <br />
            Maximale Dateigröße: 10 MB
            <br />
            Minimale Bildhöhe: 512 Pixel
        </b-form-text>
    </div>
</template>

<script>
/**
 * @component FormFieldFileUpload
 * @desc File uploader with validation
 * @author Jonas Pfannkuche
 */

export default {
    name: 'FormFieldFileUpload',
    props: {
        replace: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            /**
             * @member {Object} image - Uploaded image
             */
            image: null,
        }
    },
    computed: {
        /**
         * @computed acceptedFile - Returns true if a file was uploaded, type is jp(e)?g or png and size is less than 10 MB
         */
        acceptedFile() {
            // if no image was uploaded no need to check image properties
            if (!this.image) {
                return null
            }

            // check if image type is supported
            if (!['image/jpg', 'image/jpeg', 'image/png'].includes(this.image.type)) {
                return false
            }

            // check if image size is under 10 MB
            return this.image.size <= 10 * 2 ** 20
        },
    },
}
</script>

<style lang="scss" scoped>
.b-form-file ::v-deep label {
    overflow: hidden;
    word-break: break-all;

    &::after {
        border-left: 0.5rem solid $white;
    }
}
</style>
