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

        this.options = { ...DEFAULTS, ...options }
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

    setScope(jwtToken) {
        const decoded = jwtDecode(jwtToken)
        const realmAccessRoles = decoded.realm_access.roles

        for (let i = 0; i < realmAccessRoles.length; i += 1) {
            if (
                realmAccessRoles[i] === 'customer' ||
                realmAccessRoles[i] === 'employee' ||
                realmAccessRoles[i] === 'admin'
            ) {
                this.$auth.scope = realmAccessRoles[i]
            }
        }
    }

    mounted() {
        if (this.options.tokenRequired) {
            const token = this.$auth.syncToken(this.name)
            this._setToken(token)
        }

        return this.$auth.fetchUserOnce()
    }

    async login(endpoint) {
        const opts = {
            client_id: this.options.client_id,
            grant_type: this.options.grant_type,
        }

        const xhrData = endpoint

        if (!this.options.endpoints.login) {
            return
        }

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

        this.setScope(result)

        if (this.options.tokenRequired) {
            const token = this.options.tokenType ? `${this.options.tokenType} ${result}` : result

            this.$auth.setToken(this.name, token)
            this._setToken(token)

            this.$auth.setRefreshToken(this.name, response.data.refresh_token)
        }

        if (this.options.autoFetchUser) {
            await this.fetchUser()
        }

        return response
    }

    async setUserToken(tokenValue) {
        const token = this.options.tokenType ? `${this.options.tokenType} ${tokenValue}` : tokenValue
        this.$auth.setToken(this.name, token)
        this._setToken(token)

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
            await this.$auth.requestWith(this.name, endpoint, this.options.endpoints.logout).catch(() => {})
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

        return Promise.resolve()
    }
}
