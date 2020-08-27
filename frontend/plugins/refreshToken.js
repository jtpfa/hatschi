/*
  Refresh keycloak's access token
  This is a refactored and es6-conform version of
  https://gist.github.com/robsontenorio/d1e56c5bc5bc391ba0791be77419a68c
 */
import jwtDecode from 'jwt-decode'

const strategy = 'keycloak'

// Properly encode data
function encodeQuery(queryObject) {
    return Object.keys(queryObject)
        .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(queryObject[key])}`)
        .join('&')
}

export default function refresh({ app }) {
    const { $axios, $auth } = app

    if (!$auth.loggedIn || !$auth.strategies[strategy]) return

    const { options } = $auth.strategies.keycloak
    let token = $auth.getToken(strategy)
    let refreshToken = $auth.getRefreshToken(strategy)

    if (!token || !refreshToken) return

    // calculate timeout before token expiration (75% from expiration time)
    const tokenParsed = jwtDecode.call(this, token)
    let refreshInterval = (tokenParsed.exp * 1000 - Date.now()) * 0.75

    // Limit 10 seconds (avoid self attack)
    if (refreshInterval < 10000) {
        refreshInterval = 10000
    }

    // keep refreshing token before expiration time
    setInterval(async () => {
        try {
            const response = await $axios.post(
                options.access_token_endpoint,
                encodeQuery({
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
        } catch (error) {
            $auth.logout()
            throw new Error(`Error while refreshing token: ${error}`)
        }
    }, refreshInterval)
}
