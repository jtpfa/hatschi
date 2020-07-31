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
    extends: [
        'airbnb-base',
        'plugin:vue/recommended',
        'eslint:recommended',
        'prettier/vue',
        'plugin:prettier/recommended',
    ],
    // required to lint *.vue files
    plugins: ['vue'],
    // add your custom rules here
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
        'import/prefer-default-export': 0,
        'no-underscore-dangle': 'off',
        'consistent-return': 'off',
        'vue/no-v-html': 0,
        // @todo: remove these when products are included dynamically
        'vue/no-unused-vars': 0,
        'vue/valid-v-for': 0,
        'no-shadow': 0,
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
