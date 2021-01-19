/**
 * Inject the currency converter into the nuxt context to access it globally
 *
 * @author Jonas Pfannkuche
 */

import CurrenyConverter from '~/helpers/CurrencyConverter'

export default (context, inject) => {
    const currency = new CurrenyConverter(2, '.')

    inject('currencyConverter', currency)
}
