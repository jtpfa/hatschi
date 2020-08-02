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
        }).then(response => (response.ok ? response : throw response))
    }

    addProduct(productAttributes, userToken) {
        return fetch(`${this.baseUrl}employee/article`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(productAttributes),
        }).then(response => (response.ok ? response : throw response))
    }

    getAllProductsShortVersion() {
        return fetch(`${this.baseUrl}article`, { method: 'GET' })
            .then(response => response.json())
            .catch(error => throw error)
    }

    getAllProductsDetailedVersion(userToken) {
        return fetch(`${this.baseUrl}employee/article`, {
            method: 'GET',
            headers: {
                Authorization: userToken,
            },
        })
            .then(response => response.json())
            .catch(error => throw error)
    }

    editProduct(productAttributes, userToken) {
        return fetch(`${this.baseUrl}employee/article`, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(productAttributes),
        }).then(response => (response.ok ? response : throw response))
    }

    deleteProduct(productId, userToken) {
        return fetch(`${this.baseUrl}employee/article/${productId}`, {
            method: 'DELETE',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
        }).then(response => (response.ok ? response : throw response))
    }
}
