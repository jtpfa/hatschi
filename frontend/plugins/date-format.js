/**
 * Inject the date fromatter into the nuxt context to access it globally
 *
 * @author Jonas Pfannkuche
 */

import DateFormatter from '~/helpers/DateFormatter'

export default (context, inject) => {
    inject('dateFormatter', DateFormatter)
}
