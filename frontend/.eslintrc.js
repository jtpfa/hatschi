/**
 * ESLint configurations
 *
 * @author Jonas Pfannkuche
 */

module.exports = {
    globals: {
        $nuxt: true,
    },
    root: true,
    env: {
        browser: true,
        node: true,
    },
    parserOptions: {
        parser: 'babel-eslint',
        sourceType: 'module',
    },
    /**
     * The order in the extends array shouldn't be changed
     * Otherwise you could run into an endless loop
     */
    extends: [
        'airbnb-base',
        'plugin:vue/recommended',
        'eslint:recommended',
        'prettier/vue',
        'plugin:prettier/recommended',
    ],
    // required to lint *.vue files
    plugins: ['vue'],
    rules: {
        'import/extensions': [
            'error',
            'ignorePackages',
            {
                js: 'never',
                vue: 'never',
            },
        ],
        'vue/attributes-order': [
            'error',
            {
                order: [
                    'DEFINITION',
                    'LIST_RENDERING',
                    'CONDITIONALS',
                    'RENDER_MODIFIERS',
                    'GLOBAL',
                    'UNIQUE',
                    'TWO_WAY_BINDING',
                    'OTHER_DIRECTIVES',
                    'OTHER_ATTR',
                    'EVENTS',
                    'CONTENT',
                ],
                alphabetical: true,
            },
        ],
        'no-underscore-dangle': 'off',
        'consistent-return': 'off',
        'vue/no-v-html': 0,
        'no-param-reassign': ['error', { props: false }],
        'no-shadow': ['error', { allow: ['resolve', 'reject', 'state'] }],
    },
    settings: {
        'import/resolver': {
            nuxt: {
                extensions: ['.js', '.vue'],
            },
        },
        'import/core-modules': ['vue', 'vuex'],
    },
}
