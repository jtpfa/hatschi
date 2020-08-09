import { TextCropper } from '~/helpers/TextCropper'

export default (context, inject) => {
    inject('textCropper', TextCropper)
}
