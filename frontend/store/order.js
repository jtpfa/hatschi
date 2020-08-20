export const state = () => ({
    shippingMethod: {
        id: '',
        name: '',
        description: '',
    },
    shippingAddress: {
        firstName: '',
        lastName: '',
        zip: '',
        city: '',
        street: '',
        number: '',
        country: '',
        additionalAddress: '',
    },
    differentInvoiceAddress: false,
    invoiceAddress: {
        firstName: '',
        lastName: '',
        zip: '',
        city: '',
        street: '',
        number: '',
        country: '',
        additionalAddress: '',
    },
    paymentMethod: {
        id: '',
        name: '',
        description: '',
    },
    giftCardCode: '',
    discountCode: '',
    step: 1,
})

export const mutations = {
    updateOrderInformation: (state, payload) => {
        if (payload.address && payload.key) {
            state[payload.address][payload.key] = payload.data
        } else {
            state[payload.key] = payload.data
        }
    },
}
