<template>
    <ul
        class="p-1 border border-top-0 rounded"
        :class="{
            'border-danger': passwordStrength < passwordCriteria.length,
            'border-success': (passwordStrength = passwordCriteria.length),
        }"
    >
        <li
            v-for="criteria in passwordCriteria"
            :key="criteria.text"
            class="d-flex justify-content-between align-items-center"
        >
            {{ criteria.text }}

            <b-badge v-if="criteria.fulfilled" pill variant="success">&#10003;</b-badge>
            <b-badge v-else pill variant="danger">&#10007;</b-badge>
        </li>
    </ul>
</template>

<script>
export default {
    name: 'FormFieldPasswordStrength',
    props: {
        password: {
            type: String,
            default: '',
            required: true,
        },
    },
    data() {
        return {
            passwordCriteria: [
                { text: 'Mind. 6 Zeichen', fulfilled: false },
                { text: 'Mind. einen Buchstaben', fulfilled: false },
                { text: 'Mind. eine Ziffer (0-9)', fulfilled: false },
                { text: 'Mind. ein Sonderzeichen (!§$%&/()=?|{}[]+#;:.,@€_-)', fulfilled: false },
            ],
        }
    },
    computed: {
        passwordStrength: {
            get() {
                let strength = 0
                this.passwordCriteria.forEach(criteria => {
                    strength += criteria.fulfilled ? 1 : 0
                })
                return strength
            },
            set() {},
        },
    },
    watch: {
        password() {
            this.$set(this.passwordCriteria, 0, {
                text: this.passwordCriteria[0].text,
                fulfilled: /^.{6,120}$/.test(this.password),
            })

            this.$set(this.passwordCriteria, 1, {
                text: this.passwordCriteria[1].text,
                fulfilled: /([a-zA-Z])/.test(this.password),
            })

            this.$set(this.passwordCriteria, 2, {
                text: this.passwordCriteria[2].text,
                fulfilled: /([\d])/.test(this.password),
            })

            this.$set(this.passwordCriteria, 3, {
                text: this.passwordCriteria[3].text,
                fulfilled: /([!§$%&/()=?|{}[\]+#;:.,@€_-])/.test(this.password),
            })
        },
    },
}
</script>

<style scoped></style>
