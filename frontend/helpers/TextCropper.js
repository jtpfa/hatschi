/**
 * @module TextCropper
 * @author Jonas Pfannkuche
 */
class TextCropper {
    /**
     * Crops a text and appends '...'
     * @function cropText
     * @static
     * @param {String} text - Text which should be cropped
     * @param {Number} length - Length of the cropped text
     * @returns {String}
     */
    static cropText(text, length) {
        if (!text || !length) {
            return ''
        }
        // text needs no cropping
        if (text.length < length) {
            return text
        }
        let result = text.substring(0, length - 1)

        // Add a space if the cropped text doesn't end with one
        if (result.slice(-1) !== ' ') {
            result += ' '
        }

        result += '...'

        return result
    }
}

export default TextCropper
