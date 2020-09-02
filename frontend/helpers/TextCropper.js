export class TextCropper {
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
