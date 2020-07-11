<template>
    <b-container>
        <registration-form/>
    </b-container>
</template>

<script>

  import LoginForm from '../components/login/form'
  import RegistrationForm from '../components/registration/form'

  export default {
    components: {
      LoginForm,
      RegistrationForm
    }
  }
</script>

<style>

</style>
