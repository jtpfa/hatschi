import { RestApi } from '~/helpers/RestApi'

export default (context, inject) => {
    const api = new RestApi('http://localhost:8080/api/')

    inject('api', api)
}
