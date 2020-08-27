export const state = () => ({
    shippingMethod: {
        id: '',
        name: '',
        description: '',
    },
    addresses: [],
    shippingAddress: -1,
    differentInvoiceAddress: false,
    invoiceAddress: -1,
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
