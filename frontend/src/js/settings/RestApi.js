export default class RestApi {
    constructor() {
        this.url = 'http://localhost:8080/api/registration'
    }

    SignUp() {
        Promise((resolve, reject) => {
            fetch(this.url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            })
                .then(response => resolve(response))
                .catch(err => reject(err))
        })
    }
}
