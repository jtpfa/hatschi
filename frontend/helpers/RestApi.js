const _handleResponse = (response, needJsonOutput = false) => {
    if (response.ok) {
        return needJsonOutput ? response.json() : response
    }
    if (response.status === 401) {
        throw new Error('Zugriff nicht gestattet. Bitte (erneut) anmelden.')
    } else {
        return response.json().then(result => (result.error !== '' ? throw result : result))
    }
}

export class RestApi {
    constructor(baseUrl) {
        this.baseUrl = baseUrl
    }

    signUp(userAttributes) {
        return fetch(`${this.baseUrl}registration`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify(userAttributes),
        }).then(response => _handleResponse(response))
    }

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

    getProduct(productId) {
        return fetch(`${this.baseUrl}article/${productId}`, { method: 'GET' }).then(response =>
            _handleResponse(response, true)
        )
    }

    getAllProductsShortVersion() {
        return fetch(`${this.baseUrl}article`, { method: 'GET' }).then(response => _handleResponse(response, true))
    }

    getAllProductsDetailedVersion(userToken) {
        return fetch(`${this.baseUrl}employee/article`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

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

    deleteProduct(productId, userToken) {
        return fetch(`${this.baseUrl}employee/article/${productId}`, {
            method: 'DELETE',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response))
    }

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

    getAllOrders(userToken) {
        return fetch(`${this.baseUrl}employee/order`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

    getAllOrdersOfCustomer(userToken) {
        return fetch(`${this.baseUrl}customer/order`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

    getAddressesOfCustomer(userToken) {
        return fetch(`${this.baseUrl}customer/address`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response, true))
    }

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

    deleteCustomerAddress(addressId, userToken) {
        return fetch(`${this.baseUrl}customer/address/${addressId}`, {
            method: 'DELETE',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
        }).then(response => _handleResponse(response))
    }

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
}
