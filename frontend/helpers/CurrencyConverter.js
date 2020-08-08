export class CurrenyConverter {
    constructor(fractionCount, fractionSeperator) {
        this.fractionCount = fractionCount
        this.fractionSeperator = fractionSeperator
    }

    insertFractionForEuroConversation(priceInCents) {
        if (!priceInCents) {
            return 0
        }

        let price = priceInCents.toString()

        price =
            price.substr(0, price.length - this.fractionCount) +
            this.fractionSeperator +
            price.substr(-this.fractionCount)

        return price
    }
}
