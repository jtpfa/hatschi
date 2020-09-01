export default {
    mode: 'universal',
    /*
     ** Headers of the page
     */
    head: {
        htmlAttrs: {
            lang: 'de-DE',
        },
        title: 'PC Masterrace – Dein Online-Shop für PC-Equipment',
        meta: [
            { charset: 'utf-8' },
            { name: 'viewport', content: 'width=device-width, initial-scale=1' },
            {
                hid: 'description',
                name: 'description',
                content: process.env.npm_package_description || '',
            },
        ],
        link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
    },
    /*
     ** Customize the progress-bar color
     */
    loading: false,
    /*
     ** Global CSS
     */
    css: ['~/assets/scss/main.scss'],
    /*
     ** Plugins to load before mounting the App
     */
    plugins: [
        { src: '~/plugins/api', ssr: true },
        { src: '~/plugins/carousel', ssr: true },
        { src: '~/plugins/currency', ssr: true },
        { src: '~/plugins/date-format', ssr: true },
        { src: '~/plugins/image-src-set', ssr: true },
        { src: '~/plugins/text-crop', ssr: true },
        { src: '~/plugins/vuex-persist', ssr: false },
    ],
    /*
     ** Nuxt.js dev-modules
     */
    buildModules: [],
    /*
     ** Nuxt.js modules
     */
    modules: [
        // https://github.com/nuxt-community/style-resources-module
        '@nuxtjs/style-resources',
        // Doc: https://bootstrap-vue.js.org/docs/
        'bootstrap-vue/nuxt',
        '@nuxtjs/axios',
        '@nuxtjs/auth',
        [
            'nuxt-cookie-control',
            {
                controlButton: false,
                text: {
                    barDescription:
                        'Wir verwenden unsere eigenen Cookies, damit wir Ihnen diese Website zeigen können. Wenn Sie weiter surfen, gehen wir davon aus, dass Sie die Cookies akzeptiert haben.',
                    optional: '',
                },
            },
        ],
    ],
    /*
     * Cookie options for Cookie Consent Manager
     */
    cookies: {
        necessary: [
            {
                name: 'Default Cookies',
                description: 'für den Cookie Consent genutzt.',
                cookies: ['cookie_control_consent', 'cookie_control_enabled_cookies'],
            },
            {
                name: 'Cookies Authentifizierung',
                description: 'für den Login und geschützte Bereiche.',
                cookies: ['auth.strategy', 'auth._token.keycloak', 'auth._refresh_token.keycloak'],
            },
        ],
        optional: [],
    },
    /*
     ** Bootstrap Vue module configuration
     */
    bootstrapVue: {
        bootstrapCSS: false, // Or `css: false`
        bootstrapVueCSS: false, // Or `bvCSS: false`
    },
    /*
     ** Style Resources module configuration
     */
    styleResources: {
        scss: ['~assets/scss/settings/variables.scss'],
    },
    /*
     ** Build configuration
     */
    build: {
        /*
         ** You can extend webpack config here
         */
        /* eslint-disable no-unused-vars */
        extend(config, ctx) {},
        /* eslint-enable no-unused-vars */
        transpile: ['@nuxtjs/auth'],

        babel: {
            plugins: ['@babel/plugin-proposal-throw-expressions'],
            compact: true,
        },
    },
    publicRuntimeConfig: {
        baseURL: process.env.PCMR_BASE_URL_PROD || 'http://localhost:3000',
        restApiBaseUrl: process.env.PCMR_REST_API_PROD || 'http://localhost:8090/api/',
        mediaUrl: process.env.PCMR_MEDIA_API_PROD || 'http://localhost:8090/media/article/',
        keycloakTokenEndpoint:
            process.env.KEYCLOAK_TOKEN_ENDPOINT_PROD ||
            'http://auth.pcmr.de:8080/auth/realms/pcmr/protocol/openid-connect/token',
        keycloakLogoutEndpoint:
            process.env.KEYCLOAK_LOGOUT_ENDPOINT_PROD ||
            'http://auth.pcmr.de:8080/auth/realms/pcmr/protocol/openid-connect/logout',
    },
    auth: {
        plugins: [{ src: '~/plugins/refresh', ssr: false }],
        strategies: {
            keycloak: {
                _scheme: '~/schemes/keycloak',
                token_key: 'access_token',
                token_type: 'Bearer',
                grant_type: 'password',
                client_id: 'pcmr',
                token: {
                    property: 'access_token',
                    maxAge: 300,
                },
                refreshToken: {
                    property: 'refresh_token',
                },
                autoRefresh: {
                    enable: true,
                },
            },
        },
        redirect: {
            login: '/auth/login',
            logout: '/',
            home: '/',
            callback: false,
        },
        resetOnError: true,
        defaultStrategy: 'keycloak',
    },
}
