export const state = () => ({
    addresses: [],
    shippingMethod: {
        id: '',
        name: '',
        description: '',
    },
    shippingAddress: {
        id: -1,
        index: -1,
    },
    differentInvoiceAddress: false,
    invoiceAddress: {
        id: -1,
        index: -1,
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
        state[payload.key] = payload.data
    },
}
