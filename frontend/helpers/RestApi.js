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
        }).then(response => (response.ok ? Promise.resolve(response) : Promise.reject(response)))
        // @todo error handling
    }

    addProduct(productAttributes, userToken) {
        return fetch(`${this.baseUrl}employee/article`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(productAttributes),
        }).then(response => (response.ok ? Promise.resolve(response) : Promise.reject(response)))
        // @todo error handling
    }

    getAllProducts() {
        return fetch(`${this.baseUrl}article`, { method: 'GET' }).then(response => response.json())
        // @todo error handling
    }

    editProduct(productAttributes, userToken) {
        return fetch(`${this.baseUrl}employee/article`, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(productAttributes),
        }).then(response => (response.ok ? Promise.resolve(response) : Promise.reject(response)))
        // @todo error handling
    }

    deleteProduct(productId, userToken) {
        return fetch(`${this.baseUrl}employee/article/${productId}`, {
            method: 'DELETE',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
        }).then(response => (response.ok ? Promise.resolve(response) : Promise.reject(response)))
    }
}
