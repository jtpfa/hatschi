/**
 * @module CurrencyConverter
 * @author Jonas Pfannkuche
 */

class CurrenyConverter {
    /**
     * @param {Number} fractionCount - Number of decimal places
     * @param {string} fractionSeperator - Separator between euro and cents
     */
    constructor(fractionCount, fractionSeperator) {
        this.fractionCount = fractionCount
        this.fractionSeperator = fractionSeperator
    }

    /**
     * Converts cents to euro
     * @param {Number} priceInCents - Price, in cents, to convert to euro
     * @returns {String}
     */
    convertCentsToEuro(priceInCents) {
        if (priceInCents === undefined) {
            return new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR' }).format(0)
        }

        let price = priceInCents.toString()

        price =
            price.substr(0, price.length - this.fractionCount) +
            this.fractionSeperator +
            price.substr(-this.fractionCount)

        return new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'EUR' }).format(price)
    }
}

export default CurrenyConverter
