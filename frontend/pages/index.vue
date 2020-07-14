<template>
  <b-container>
    <registration-form />
    <b-button @click="submit" />
    <h2>{{ status }}</h2>
  </b-container>
</template>

<script>
import RegistrationForm from "../components/registration/form";

export default {
  components: {
    RegistrationForm,
  },
  methods: {
    async submit() {
      try {
        let response = await this.$auth.loginWith("keycloak", { data: "1234" });
        console.log(response)
      } catch (err) {
        console.log(err)
      }
    },
  },
  computed: {
    status() {
      return this.$auth.user;
    },
  },
};
</script>

<style></style>
