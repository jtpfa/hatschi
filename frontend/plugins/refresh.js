/**
 * Refresh keycloak's access token
 * This is a refactored and es6-conform version of
 * {@link https://gist.github.com/robsontenorio/d1e56c5bc5bc391ba0791be77419a68c https://gist.github.com/robsontenorio/d1e56c5bc5bc391ba0791be77419a68c}
 *
 * Two main things have been changed:
 * 1. We use the jqt-decode module instead of robson's selfmade decode function
 * 2. We fetch the user after a successful access token refreshing so we can use
 * the user attributes as placeholders in forms
 *
 * @author Jonas Pfannkuche
 */

import jwtDecode from 'jwt-decode'

const strategy = 'keycloak'

/**
 * Encode query data
 * @private
 * @param {Object} queryObject - Object of query parts
 * @param {string} queryObject.refresh_token - Refresh token of user
 * @param {string} queryObject.client_id - Client id of keycloak realm
 * @param {string} queryObject.grant_type - Grant type of token flow
 * @returns {string} - Encoded query
 */
function _encodeQuery(queryObject) {
    return Object.keys(queryObject)
        .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(queryObject[key])}`)
        .join('&')
}

/**
 * Refreshes access token with the refresh token before the current access token is invalid
 * @param {Object} app - Nuxt app context
 */
export default function refresh({ app }) {
    const { $axios, $auth } = app

    if (!$auth.loggedIn || !$auth.strategies[strategy]) {
        return
    }

    const { options } = $auth.strategies.keycloak
    let token = $auth.getToken(strategy)
    let refreshToken = $auth.getRefreshToken(strategy)

    if (!token || !refreshToken) return

    // calculate timeout before token expiration (75% from expiration time)
    const tokenParsed = jwtDecode.call(this, token)
    const refreshInterval = (tokenParsed.exp * 1000 - Date.now()) * 0.75

    // keep refreshing token before expiration time
    setInterval(async () => {
        try {
            const response = await $axios.post(
                options.access_token_endpoint,
                _encodeQuery({
                    refresh_token: refreshToken.replace(`${options.token_type} `, ''),
                    client_id: options.client_id,
                    grant_type: options.refreshToken.property,
                })
            )

            token = `${options.token_type} ${response.data.access_token}`
            refreshToken = `${options.token_type} ${response.data.refresh_token}`

            $auth.setToken(strategy, token)
            $auth.setRefreshToken(strategy, refreshToken)
            $axios.setToken(token)
            await $auth.fetchUser()
        } catch (error) {
            $auth.logout()
            throw new Error('Error while refreshing token')
        }
    }, refreshInterval)
}
