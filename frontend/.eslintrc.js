module.exports = {
    env: {
        browser: true,
        es2020: true,
    },
    extends: ['airbnb-base', 'eslint:recommended', 'plugin:prettier/recommended'],
    parserOptions: {
        ecmaVersion: 11,
        sourceType: 'module',
    },
    rules: {
        'no-console': 'error',
        'import/extensions': [
            'error',
            'ignorePackages',
            {
                js: 'never',
            },
        ],
    },
}
