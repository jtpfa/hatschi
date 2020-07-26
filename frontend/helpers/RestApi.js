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
    }
}
