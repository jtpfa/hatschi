/**
 * Inject the text cropper into the nuxt context to access it globally
 *
 * @author Jonas Pfannkuche
 */

import TextCropper from '~/helpers/TextCropper'

export default (context, inject) => {
    inject('textCropper', TextCropper)
}
