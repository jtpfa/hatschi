/**
 * @store ShoppingCart
 * @namespaced
 * @desc Vuex store module to handle the shopping cart
 * @author Jonas Pfannkuche
 */

/**
 * The state object of the shopping cart
 */
export const state = () => ({
    /**
     * @member {Array} cart - Elements in shopping cart
     */
    cart: [],
    /**
     * @member {Boolean} stockOfElementChanged - Stores whether the stock has changed or not, needs to be resetted after each product
     */
    stockOfElementChanged: false,
})

/**
 * Getters to get "calculated" values of the state object
 */
export const getters = {
    /**
     * Returns the amount the different shopping cart items
     * @getter cartCountElements
     * @param {Object} state
     * @returns {Number}
     */
    cartCountElements: state => {
        return state.cart.length || 0
    },

    /**
     * Returns the total price of all shopping cart items
     * @getter cartTotal
     * @param {Object} state
     * @returns {Number}
     */
    cartTotal: state => {
        if (!state.cart.length) return 0
        return state.cart.reduce((ac, next) => ac + next.quantity * next.price, 0)
    },

    /**
     * Returns the quantity of the given product id
     * @getter productQuantity
     * @param {Object} state
     * @param {Number} state.productId - Id of the product
     * @returns {function(*): *|Number}
     */
    productQuantity: state => productId => {
        const itemfound = state.cart.find(el => el.id === productId)

        return itemfound ? itemfound.quantity : 0
    },
}

/**
 * Mutations to change state data
 */
export const mutations = {
    /**
     * Adds product with the specified quantity to the card.
     * If the product already exists, the quantity is increased accordingly
     * @mutation addToCart
     * @param {Object} state
     * @param {Object} payload - Product data
     * @param {Number} payload.id - Id of the product
     * @param {Number} payload.quantity - Product quantity to add to the cart
     */
    addToCart: (state, payload) => {
        const itemfound = state.cart.find(el => el.id === payload.id)

        if (itemfound) {
            itemfound.quantity += payload.quantity
        } else {
            state.cart.push(payload)
        }
    },
    /**
     * Empties the shopping cart
     * @mutation clearCart
     * @param {Object} state
     */
    clearCart: state => {
        Object.assign(state, { cart: [], stockOfElementChanged: false })
    },
    /**
     * Reduces the quantity of the given product by one.
     * If the quantity is one, the product will be removed from the cart
     * @mutation removeOneFromCart
     * @param {Object} state
     * @param {Object} payload - Product data
     * @param {Number} payload.id - Id of the product
     */
    removeOneFromCart: (state, payload) => {
        const index = state.cart.findIndex(el => el.id === payload.id)

        if (state.cart[index].quantity) {
            state.cart[index].quantity -= 1
        } else {
            state.cart.splice(index, 1)
        }
    },
    /**
     * Deletes the given product from the cart
     * @mutation removeAllFromCart
     * @param {Object} state
     * @param {Object} payload - Product data
     * @param {Number} payload.id - Id of the product
     */
    removeAllFromCart: (state, payload) => {
        state.cart = state.cart.filter(el => el.id !== payload.id)
    },
    /**
     * Updates the content of the given product.
     * "Stores" if the value has changed
     * @mutation updateCart
     * @param {Object} state
     * @param {Object} payload - Product data
     * @param {Number} payload.id - Id of the product
     */
    updateCart: (state, payload) => {
        state.stockOfElementChanged = false
        const itemfound = state.cart.find(el => el.id === payload.id)

        if (itemfound) {
            Object.assign(itemfound, payload)

            if (itemfound.quantity > itemfound.stock) {
                itemfound.quantity = itemfound.stock

                state.stockOfElementChanged = true
            }
        }
    },
}
