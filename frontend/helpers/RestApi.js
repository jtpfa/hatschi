// @todo adjust response handling to show error messages from backend
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

    updateUserData(userAttributes, userToken) {
        return fetch(`${this.baseUrl}customer`, {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
                Authorization: userToken,
            },
            body: JSON.stringify(userAttributes),
        }).then(response => (response.ok ? response : throw response))
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
        }).then(response =>
            response.ok
                ? response
                : response.json().then(result => (result.error !== '' ? throw result.message : result))
        )
    }

    getProduct(productId) {
        return fetch(`${this.baseUrl}article/${productId}`, { method: 'GET' })
            .then(response => response.json())
            .catch(error => throw error)
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
        }).then(response =>
            response.ok
                ? response
                : response.json().then(result => (result.error !== '' ? throw result.message : result))
        )
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
