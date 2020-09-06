/**
 * @module RestApi
 * @author Jonas Pfannkuche
 */

/**
 * Handles response and returns one of the following:
 * 1. Response is successful and needJsonOutput is true: json of response
 * 2. Response is successful and needJsonOutput is false: response
 * 3. Response status is 401: throws error (forbidden) with link to login
 * 4. Response failed and has an error message: error message
 * 5. Response failed and has no error message: json of response
 * @param {Object} response
 * @param {Boolean} [needJsonOutput=false]
 * @returns {Promise<*>|any|{ok}}
 * @private
 */
const _handleResponse = (response, needJsonOutput = false) => {
    if (response.ok) {
        return needJsonOutput ? response.json() : response
    }
    if (response.status === 401) {
        throw new Error('Zugriff nicht gestattet. Bitte (erneut) <a href="/auth/login" target="_self">anmelden</a>.')
    } else {
        return response.json().then(result => (result.error !== '' ? throw result : result))
    }
}

class RestApi {
    /**
     * @param {String} baseUrl - Base url of the REST api
     */
    constructor(baseUrl) {
        this.baseUrl = baseUrl
    }

    // ---------------------------------------------------------------
    // User account data related api calls
    // ---------------------------------------------------------------

    /**
     * Signs a new user up (with the email as the username)
     * @param {Object} userAttributes - User data needed for registration
     * @param {String} userAttributes.firstName - First name of user
     * @param {String} userAttributes.lastName - Last name of user
     * @param {String} userAttributes.email - Email of user
     * @param {String} userAttributes.password - Password of user
     * @returns {Promise<Response>}
     */
    signUp(userAttributes) {
        return fetch(`${this.baseUrl}registration`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify(userAttributes),
        }).then(response => _handleResponse(response))
    }

    /**
     * Edits the user data
     * @param {Object} userAttributes - New user data
     * @param {String} userAttributes.firstName - First name of user
     * @param {String} userAttributes.lastName - Last name of user
     * @param {String} userAttributes.email - Email of user
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    updateUserData(userAttributes, userToken) {
        return fetch(`${this.baseUrl}customer`, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(userAttributes),
        }).then(response => _handleResponse(response))
    }

    /**
     * Changes the user password
     * @param {Object} passwords - Old and new passwords
     * @param {String} passwords.currentPassword - Current password of user
     * @param {String} passwords.newPassword - New password of user
     * @param {String} passwords.confirmation - Confirmed new password of user
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    changeUserPassword(passwords, userToken) {
        return fetch(`${this.baseUrl}customer/password`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(passwords),
        }).then(response => _handleResponse(response))
    }

    // ---------------------------------------------------------------
    // Product related api calls
    // ---------------------------------------------------------------

    /**
     * Adds a new product
     * @param {Object} productAttributes - Product data needed to add it
     * @param {String} productAttributes.name - Name of the product
     * @param {String} productAttributes.description - Description of the product
     * @param {String} productAttributes.details - Details of the product
     * @param {Number} productAttributes.price - Price of the product in cents
     * @param {Number} productAttributes.stock - Stock of the product
     * @param {File|null} productImage - Image of the product
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    addProduct(productAttributes, productImage, userToken) {
        const formdata = new FormData()

        formdata.append(
            'json',
            new Blob([JSON.stringify(productAttributes)], {
                type: 'application/json',
            })
        )

        if (productImage) {
            formdata.append('file', productImage)
        }

        return fetch(`${this.baseUrl}employee/article`, {
            method: 'POST',
            headers: {
                Authorization: userToken,
            },
            body: formdata,
        }).then(response => _handleResponse(response))
    }

    /**
     * Get all information about the product
     * @param {Number} productId - Id of the product
     * @returns {Promise<Response>}
     */
    getProduct(productId) {
        return fetch(`${this.baseUrl}article/${productId}`, { method: 'GET' }).then(response =>
            _handleResponse(response, true)
        )
    }

    /**
     * Get all products in a short version for overview cards (name, description, price)
     * @returns {Promise<Response>}
     */
    getAllProductsShortVersion() {
        return fetch(`${this.baseUrl}article`, { method: 'GET' }).then(response => _handleResponse(response, true))
    }

    /**
     * Get all products with all information
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    getAllProductsDetailedVersion(userToken) {
        return fetch(`${this.baseUrl}employee/article`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

    /**
     * Get random products
     * @param {Number} excludeProductId - Id of the product that shouldn't be in the result
     * @param {Number} amount - Number of products that should be in the result
     * @returns {Promise<Response>}
     */
    getRandomProducts(excludeProductId, amount) {
        return fetch(`${this.baseUrl}article/random/${excludeProductId}/${amount}`, {
            method: 'GET',
        }).then(response => _handleResponse(response, true))
    }

    /**
     * Edits the product
     * @param {Object} productAttributes - Product data needed to add it
     * @param {String} productAttributes.id - Id of the product
     * @param {String} productAttributes.name - Name of the product
     * @param {String} productAttributes.description - Description of the product
     * @param {String} productAttributes.details - Details of the product
     * @param {Number} productAttributes.price - Price of the product in cents
     * @param {Number} productAttributes.stock - Stock of the product
     * @param {File|null} productImage - Image of the product
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    editProduct(productAttributes, productImage, userToken) {
        const formdata = new FormData()

        formdata.append(
            'json',
            new Blob([JSON.stringify(productAttributes)], {
                type: 'application/json',
            })
        )

        if (productImage) {
            formdata.append('file', productImage)
        }

        return fetch(`${this.baseUrl}employee/article`, {
            method: 'PUT',
            headers: {
                Authorization: userToken,
            },
            body: formdata,
        }).then(response => _handleResponse(response))
    }

    /**
     * Deletes the product
     * @param {Number} productId - Id of the product
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    deleteProduct(productId, userToken) {
        return fetch(`${this.baseUrl}employee/article/${productId}`, {
            method: 'DELETE',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response))
    }

    // ---------------------------------------------------------------
    // Order related api calls
    // ---------------------------------------------------------------

    /**
     * Places a new order
     * @param {Object} orderAttributes - Order information
     * @param {Object} orderAttributes.orderItems - Products of the order
     * @param {Number} orderAttributes.orderItems.articleId - Id of the ordered product
     * @param {Number} orderAttributes.orderItems.quantity - Quantity of the ordered product
     * @param {Number} orderAttributes.shippingAddressId - Id of the shipping address
     * @param {Number} orderAttributes.invoiceAddressId - Id of the invoice address
     * @param {Number} orderAttributes.paymentMethod - Id of the payment method
     * @param {Number} orderAttributes.shippingMethod - Id of the shipping method
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    placeOrder(orderAttributes, userToken) {
        return fetch(`${this.baseUrl}customer/order`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(orderAttributes),
        }).then(response => _handleResponse(response))
    }

    /**
     * Get all orders
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    getAllOrders(userToken) {
        return fetch(`${this.baseUrl}employee/order`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

    /**
     * Get all orders of logged in user (here in role as a customer)
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    getAllOrdersOfCustomer(userToken) {
        return fetch(`${this.baseUrl}customer/order`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

    // ---------------------------------------------------------------
    // Address related api calls
    // ---------------------------------------------------------------

    /**
     * Adds a new address to the customer
     * @param {Object} addressAttributes - Address data
     * @param {String} addressAttributes.firstName - First name of the address
     * @param {String} addressAttributes.lastName - Last name of the address
     * @param {String} addressAttributes.address - Street and house number of the address
     * @param {String} addressAttributes.additionalAddress - Additonal address of the address
     * @param {String} addressAttributes.zip - Zip of the address
     * @param {String} addressAttributes.city - City of the address
     * @param {String} addressAttributes.country - Country of the address
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    addCustomerAddress(addressAttributes, userToken) {
        return fetch(`${this.baseUrl}customer/address`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(addressAttributes),
        }).then(response => _handleResponse(response))
    }

    /**
     * Get all addresses of the logged in customer
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    getAddressesOfCustomer(userToken) {
        return fetch(`${this.baseUrl}customer/address`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

    /**
     * Edits an address of a logged in customer
     * @param {Object} addressAttributes - Address data
     * @param {String} addressAttributes.firstName - First name of the address
     * @param {String} addressAttributes.lastName - Last name of the address
     * @param {String} addressAttributes.address - Street and house number of the address
     * @param {String} addressAttributes.additionalAddress - Additonal address of the address
     * @param {String} addressAttributes.zip - Zip of the address
     * @param {String} addressAttributes.city - City of the address
     * @param {String} addressAttributes.country - Country of the address
     * @param {Number} addressId -  Id of the address
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    editCustomerAddress(addressAttributes, addressId, userToken) {
        return fetch(`${this.baseUrl}customer/address/${addressId}`, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(addressAttributes),
        }).then(response => _handleResponse(response))
    }

    /**
     * Deletes an address
     * @param {Number} addressId -  Id of the address
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    deleteCustomerAddress(addressId, userToken) {
        return fetch(`${this.baseUrl}customer/address/${addressId}`, {
            method: 'DELETE',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response))
    }

    // ---------------------------------------------------------------
    // User management related api calls
    // ---------------------------------------------------------------

    /**
     * Get all customers
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    getCustomers(userToken) {
        return fetch(`${this.baseUrl}employee/customer`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

    /**
     * Get all employees
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    getEmployees(userToken) {
        return fetch(`${this.baseUrl}employee/employee`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

    /**
     * Get all admins
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    getAdmins(userToken) {
        return fetch(`${this.baseUrl}admin/admin`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

    /**
     * Edits a customer
     * @param {Object} customerAttributes - New customer data
     * @param {String} customerAttributes.firstName - First name of customer
     * @param {String} customerAttributes.lastName - Last name of customer
     * @param {String} customerAttributes.email - Email of customer
     * @param {String} username - Current email of customer
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    editCustomer(customerAttributes, username, userToken) {
        return fetch(`${this.baseUrl}employee/customer/${username}`, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(customerAttributes),
        }).then(response => _handleResponse(response))
    }

    /**
     * Edits an employee
     * @param {Object} employeeAttributes - New employee data
     * @param {String} employeeAttributes.firstName - First name of employee
     * @param {String} employeeAttributes.lastName - Last name of employee
     * @param {String} employeeAttributes.email - Email of employee
     * @param {String} employeeAttributes.customerRole - Role of employee
     * @param {String} username - Current email of employee
     * @param {String} userRole - Current role of employee
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    editUser(employeeAttributes, username, userRole, userToken) {
        return fetch(`${this.baseUrl}admin/${userRole}/${username}`, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(employeeAttributes),
        }).then(response => _handleResponse(response))
    }

    /**
     * Deletes an user
     * @param {String} username - Email of the user
     * @param {string} userRole - Current role of the user
     * @param {String} userToken - Access token of the logged in user
     * @returns {Promise<Response>}
     */
    deleteUser(username, userRole, userToken) {
        return fetch(`${this.baseUrl}admin/${userRole}/${username}`, {
            method: 'DELETE',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response))
    }
}

export default RestApi
