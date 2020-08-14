import ImageSrcSet from '~/helpers/ImageSrcSet'

export default (context, inject) => {
    const imageSrcSet = new ImageSrcSet(context.$config.mediaUrl)

    inject('imageSrcSet', imageSrcSet)
}
