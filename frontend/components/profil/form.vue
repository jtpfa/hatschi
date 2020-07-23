<template>
    <b-form>
        <div class="mb-4" role="group">
            <label for="firstname">
                Vorname
            </label>
            <b-form-input id="firstname" disabled :value="user.firstName" />
        </div>

        <div class="mb-4" role="group">
            <label for="lastname">
                Nachname
            </label>
            <b-form-input id="lastname" disabled :value="user.lastName" />
        </div>

        <div class="mb-4" role="group">
            <label for="email">
                E-Mail
            </label>
            <b-form-input id="email" disabled :value="user.email" />
        </div>
    </b-form>
</template>

<script>
export default {
    name: 'ProfilForm',
    data() {
        return {
            user: this.$auth.user,
        }
    },
}
</script>

<style scoped></style>
