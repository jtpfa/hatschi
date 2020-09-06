<template>
    <div class="mb-4" role="group">
        <label for="details">
            Details
            <span class="mandatory">*</span>
        </label>
        <ckeditor
            id="details"
            ref="editorInput"
            v-model.trim="detailModel"
            class="form-control"
            :class="{ valid: details.length > 0, invalid: details.length <= 0 }"
            :config="editor.config"
            :editor="editor.editor"
            placeholder="Details"
            tag-name="textarea"
        ></ckeditor>
        <b-form-invalid-feedback id="input-live-feedback">Details angeben.</b-form-invalid-feedback>
    </div>
</template>

<script>
/**
 * @component FormFieldEditor
 * @desc WYSIWYG Editor with validation
 * @author Jonas Pfannkuche
 */

/* eslint global-require: "off" */
let ckeditor
let ClassicEditor = {}
if (process.client) {
    ClassicEditor = require('@ckeditor/ckeditor5-build-classic')
    ckeditor = require('@ckeditor/ckeditor5-vue').component
}

export default {
    name: 'FormFieldEditor',
    components: { ckeditor },
    props: {
        /**
         * @vprop {String} currentDetails - Existing details of item
         */
        currentDetails: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            /**
             * @member {String} details - If details exist user them otherwise it's an empty String
             */
            details: this.currentDetails || '',
            /**
             * @member {Object} ediotr - CKEdtior configuration
             */
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
        }
    },
    computed: {
        /**
         * @computed {String} detailModel - Synchronized input field value
         */
        detailModel: {
            get() {
                // If detail text exists use this one otherwise our new one
                return this.currentDetails.length <= 0 ? this.details : this.currentDetails
            },
            set(newDetailValue) {
                this.details = newDetailValue
            },
        },
    },
    methods: {
        /**
         * @method isValid
         * @desc Checks if the details text is between 4 and 32768 characters long
         * @returns {Boolean} true, when text is between 4 and 32768 characters long
         */
        isValid() {
            if (this.currentDetails.length <= 0) {
                return /^.{4,32768}$/.test(this.details)
            }

            return /^.{4,32768}$/.test(this.currentDetails)
        },
    },
}
</script>

<style lang="scss">
#details ~ .ck-editor {
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

.was-validated #details ~ .ck-editor {
    .ck-toolbar .ck-toolbar__items {
        background-position: right calc(0.375em + 0.1875rem) center;
        margin-right: -0.25em;
        background-repeat: no-repeat;
        background-size: calc(0.75em + 0.525rem) calc(0.75em + 0.525rem);
    }
}

.was-validated #details.invalid ~ .ck-editor {
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

.was-validated #details.valid ~ .ck-editor {
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

// show on top of bootstrap modal (1040)
.ck.ck-balloon-panel {
    z-index: 1050;
}
</style>
