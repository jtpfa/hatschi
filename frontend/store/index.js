export const state = () => ({
    cart: [],
    stockOfCartElementsChanged: false,
})

export const getters = {
    cartCountElements: state => {
        return state.cart.length || 0
    },
    cartTotal: state => {
        if (!state.cart.length) return 0
        return state.cart.reduce((ac, next) => ac + next.quantity * next.price, 0)
    },
    changedStockOfCartElements: state => {
        return state.stockOfCartElementsChanged
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
    removeOneFromCart: (state, payload) => {
        const index = state.cart.findIndex(el => el.id === payload.id)

        if (state.cart[index].quantity) {
            state.cart[index].quantity -= 1
        } else {
            state.cart.splice(index, 1)
        }
    },
    removeAllFromCart: (state, payload) => {
        state.cart = state.cart.filter(el => el.id !== payload.id)
    },
    resetStockElementsChangeListener: (state, payload = false) => {
        state.stockOfCartElementsChanged = payload
    },
    updateCart: (state, payload) => {
        const itemfound = state.cart.find(el => el.id === payload.id)

        if (itemfound) {
            Object.assign(itemfound, payload)

            if (itemfound.quantity > itemfound.stock) {
                state.stockOfCartElementsChanged = true
                itemfound.quantity = itemfound.stock
            }
        }
    },
}
