import { RestApi } from '~/helpers/RestApi'

export default (context, inject) => {
    const api = new RestApi(context.$config.restApiBaseUrl, context.$config.keycloakChangePasswordEndpoint)

    inject('api', api)
}
