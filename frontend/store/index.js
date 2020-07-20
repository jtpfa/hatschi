export const state = () => ({
    cart: [],
})

export const getters = {
    cartElements: state => {
        return state.cart.length || 0
    },
}

export const mutations = {
    addToCart: (state, payload) => {
        const itemfound = state.cart.find(el => el.id === payload.id)

        if (itemfound) {
            itemfound.quantity += payload.quantity
        } else {
            state.cart.push(payload)
        }
    },
}
