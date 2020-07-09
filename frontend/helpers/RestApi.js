export class RestApi {
    constructor(baseUrl) {
        this.baseUrl = baseUrl
    }

    SignUp(data) {
        return fetch(`${this.baseUrl}registration`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            },
            body: JSON.stringify(data),
        }).then(response => (response.ok ? Promise.resolve(response) : Promise.reject(response)))
    }
}
