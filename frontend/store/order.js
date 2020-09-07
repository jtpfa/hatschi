/**
 * @store Order
 * @namespaced
 * @desc Vuex store module to store all order related information
 * @author Jonas Pfannkuche
 */

/**
 * The state object of the order information
 */
export const state = () => ({
    /**
     * @member {Array} adresses - Selectable addresses of user
     */
    addresses: [],
    /**
     * @member {Object} shippingMethod - Stores the selected shipping method with an id, a name and a description
     */
    shippingMethod: {
        id: '',
        name: '',
        description: '',
    },
    /**
     * @member {Object} shippingAddress - Stores the selected shipping Address with the id and the list index
     */
    shippingAddress: {
        id: -1,
        index: -1,
    },
    /**
     * @member {boolean} differentInvoiceAddress - Stores whether a user has a different billing address
     */
    differentInvoiceAddress: false,
    /**
     * @member {Object} invoiceAddress - Stores the selected invoice Address with the id and the list index
     */
    invoiceAddress: {
        id: -1,
        index: -1,
    },
    /**
     * @member {Object} paymentMethod - Stores the selected payment method with an id, a name and a description
     */
    paymentMethod: {
        id: '',
        name: '',
        description: '',
    },
    /**
     * @member {string} giftCardCode - Stores the gift card code
     */
    giftCardCode: '',
    /**
     * @member {string} discountCode - Stores the discount code
     */
    discountCode: '',
    /**
     * @member {Number} step - Stores the current step of the order process
     */
    step: 1,
})

/**
 * Mutations to change state data
 */

export const mutations = {
    /**
     * Updates information of the order process
     * @mutation updateOrderInformation
     * @param {Object} state
     * @param {Object} payload
     * @param {string} payload.key - Name of attribute which should be updated
     * @param {*} payload.data - Data you want to set
     */
    updateOrderInformation: (state, payload) => {
        state[payload.key] = payload.data
    },
}
