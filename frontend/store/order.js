export const state = () => ({
    deliveryMethod: null,
    deliveryAddress: {
        firstName: '',
        lastName: '',
        zip: '',
        city: '',
        street: '',
        number: '',
    },
    invoiceAddress: {
        firstName: '',
        lastName: '',
        zip: '',
        city: '',
        street: '',
        number: '',
    },
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
