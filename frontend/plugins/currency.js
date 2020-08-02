import { CurrenyConverter } from '~/helpers/CurrencyConverter'

export default (context, inject) => {
    const currency = new CurrenyConverter(2, '.')

    inject('currencyConverter', currency)
}
