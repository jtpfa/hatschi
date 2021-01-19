/**
 * Inject the REST API into the nuxt context to access it globally
 *
 * @author Jonas Pfannkuche
 */

import RestApi from '~/helpers/RestApi'

export default (context, inject) => {
    const api = new RestApi(context.$config.restApiBaseUrl)

    inject('api', api)
}
