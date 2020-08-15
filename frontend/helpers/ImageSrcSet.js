export default class ImageSrcSet {
    constructor(mediaUrl) {
        this.baseUrl = mediaUrl
    }

    getSrcSet(productId) {
        const sizes = ['256', '512', '1024']
        let result = ''

        sizes.forEach((size, index) => {
            result += `${this.baseUrl}${productId}_${size}.png ${index + 1}x, `
        })

        // remove last colon and whitespace from result
        return result.slice(0, -2)
    }

    getImageUrl(productId, size = 512) {
        return `${this.baseUrl}${productId}_${size}.png`
    }
}
