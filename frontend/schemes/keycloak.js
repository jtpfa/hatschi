import jwtDecode from 'jwt-decode'

const DEFAULTS = {
    tokenRequired: true,
    tokenType: 'Bearer',
    globalToken: true,
    tokenName: 'Authorization',
    autoFetchUser: true,
}

export default class KeyCloakScheme {
    constructor(auth, options) {
        this.$auth = auth
        this.name = options._name

        this.options = {
            ...DEFAULTS,
            ...options,
            access_token_endpoint: this.$auth.ctx.$config.keycloakTokenEndpoint,
            endpoints: {
                login: {
                    url: this.$auth.ctx.$config.keycloakTokenEndpoint,
                    method: 'post',
                    propertyName: 'access_token',
                },
                logout: { url: this.$auth.ctx.$config.keycloakLogoutEndpoint, method: 'post' },
                refresh: {
                    url: this.$auth.ctx.$config.keycloakTokenEndpoint,
                    method: 'post',
                    propertyName: 'refresh_token',
                },
                user: { url: `${this.$auth.ctx.$config.restApiBaseUrl}customer`, method: 'get' },
            },
        }
    }

    _setToken(token) {
        if (this.options.globalToken) {
            // Set Authorization token for all axios requests
            this.$auth.ctx.app.$axios.setHeader(this.options.tokenName, token)
        }
    }

    _clearToken() {
        if (this.options.globalToken) {
            // Clear Authorization token for all axios requests
            this.$auth.ctx.app.$axios.setHeader(this.options.tokenName, false)
        }
    }

    setRoles(jwtToken) {
        const decoded = jwtDecode(jwtToken)
        const realmAccessRoles = decoded.realm_access.roles

        let roles = ''

        for (let i = 0; i < realmAccessRoles.length; i += 1) {
            if (
                realmAccessRoles[i] === 'customer' ||
                realmAccessRoles[i] === 'employee' ||
                realmAccessRoles[i] === 'admin'
            ) {
                roles += `${realmAccessRoles[i]} `
            }
        }

        // remove last colon and whitespace from roles and add them to storage
        this.$auth.$storage.setState('roles', roles.slice(0, -2))
    }

    mounted() {
        if (this.options.tokenRequired) {
            const token = this.$auth.syncToken(this.name)
            this._setToken(token)
        }

        return this.$auth.fetchUserOnce()
    }

    async login(endpoint) {
        if (!this.options.endpoints.login) {
            return
        }

        const opts = {
            client_id: this.options.client_id,
            grant_type: this.options.grant_type,
        }

        const xhrData = endpoint

        xhrData.headers = {
            'Content-Type': 'application/x-www-form-urlencoded',
        }

        const urlencoded = new URLSearchParams()
        urlencoded.append('client_id', opts.client_id)
        urlencoded.append('username', endpoint.username)
        urlencoded.append('password', endpoint.password)
        urlencoded.append('grant_type', opts.grant_type)

        xhrData.data = urlencoded

        // Ditch any leftover local tokens before attempting to log in
        await this.$auth.reset()

        const { response, result } = await this.$auth.request(xhrData, this.options.endpoints.login, true)

        this.setRoles(result)

        if (this.options.tokenRequired) {
            await this.setUserToken(result, response.data.refresh_token)
        }

        return response
    }

    async setUserToken(accessToken, refreshToken) {
        const token = this.options.tokenType ? `${this.options.tokenType} ${accessToken}` : accessToken
        this.$auth.setToken(this.name, token)
        this._setToken(token)

        this.$auth.setRefreshToken(this.name, refreshToken)

        return this.fetchUser()
    }

    async fetchUser(endpoint) {
        // Token is required but not available
        if (this.options.tokenRequired && !this.$auth.getToken(this.name)) {
            return
        }

        // User endpoint is disabled.
        if (!this.options.endpoints.user) {
            this.$auth.setUser({})
            return
        }

        // Try to fetch user and then set
        const user = await this.$auth.requestWith(this.name, endpoint, this.options.endpoints.user)
        this.$auth.setUser(user)
    }

    async logout(endpoint) {
        // Only connect to logout endpoint if it's configured
        if (this.options.endpoints.logout) {
            const opts = {
                client_id: this.options.client_id,
                // @todo global definition of clientid
            }

            const xhrData = { ...endpoint }

            xhrData.headers = {
                'Content-Type': 'application/x-www-form-urlencoded',
            }

            const urlencoded = new URLSearchParams()
            urlencoded.append('client_id', opts.client_id)
            urlencoded.append('refresh_token', this.$auth.getRefreshToken(this.name))

            xhrData.data = urlencoded

            await this.$auth.requestWith(this.name, xhrData, this.options.endpoints.logout).catch(() => {})
        }

        // But reset regardless
        return this.$auth.reset()
    }

    async reset() {
        if (this.options.tokenRequired) {
            this._clearToken()
        }

        this.$auth.setUser(false)
        this.$auth.setToken(this.name, false)
        this.$auth.setRefreshToken(this.name, false)
        this.$auth.$storage.setState('roles', false)

        return Promise.resolve()
    }
}
