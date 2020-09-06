/**
 * Inject the image source set generator into the nuxt context to access it globally
 *
 * @author Jonas Pfannkuche
 */

import ImageSrcSet from '~/helpers/ImageSrcSet'

export default (context, inject) => {
    const imageSrcSet = new ImageSrcSet(context.$config.mediaUrl)

    inject('imageSrcSet', imageSrcSet)
}
