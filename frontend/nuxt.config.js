export default {
    mode: 'universal',
    /*
     ** Headers of the page
     */
    head: {
        htmlAttrs: {
            lang: 'de-DE',
        },
        title: process.env.npm_package_name || '',
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
    loading: { color: '#fff' },
    /*
     ** Global CSS
     */
    css: ['~/assets/scss/main.scss'],
    /*
     ** Plugins to load before mounting the App
     */
    plugins: [
        { src: '~/plugins/api', ssr: true },
        { src: '~/plugins/currency', ssr: true },
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
        [
            'vue-currency-filter/nuxt',
            {
                symbol: '€',
                thousandsSeparator: '.',
                fractionCount: 2,
                fractionSeparator: ',',
                symbolPosition: 'end',
                symbolSpacing: true,
            },
        ],
        '@nuxtjs/axios',
        '@nuxtjs/auth',
    ],
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
        },
    },
    publicRuntimeConfig: {
        baseURL: process.env.PCMR_BASE_URL_PROD || 'http://localhost:3000',
        restApiBaseUrl: process.env.PCMR_REST_API_PROD || 'http://localhost:8090/api/',
        keycloakTokenEndpoint:
            process.env.KEYCLOAK_TOKEN_ENDPOINT_PROD ||
            'http://auth.pcmr.de:8080/auth/realms/pcmr/protocol/openid-connect/token',
    },
    auth: {
        strategies: {
            keycloak: {
                _scheme: '~/schemes/keycloak',
                token_key: 'access_token',
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
            callback: '/auth/login',
            home: '/',
        },
    },
}
