import { DateFormatter } from '~/helpers/DateFormatter'

export default (context, inject) => {
    inject('dateFormatter', DateFormatter)
}
