<template>
  <b-form ref="form" novalidate @submit="onSubmit">
    <b-form-row>
      <div class="col-md-6" role="group">
        <label for="firstname">Vorname<span class="mandatory">*</span></label>
        <b-form-input
          id="firstname"
          v-model="firstName"
          aria-describedby="input-live-feedback"
          autocomplete="given-name"
          placeholder="Vorname"
          required
          trim
        ></b-form-input>

        <b-form-invalid-feedback id="input-live-feedback">
          Bitte gib deinen Vornamen an.
        </b-form-invalid-feedback>
      </div>
      <div class="col-md-6" role="group">
        <label for="name">Nachname<span class="mandatory">*</span></label>
        <b-form-input
          id="name"
          v-model="lastName"
          aria-describedby="input-live-feedback"
          autocomplete="family-name"
          placeholder="Nachname"
          required
          trim
        ></b-form-input>

        <b-form-invalid-feedback id="input-live-feedback">
          Bitte gib deinen Nachnamen an.
        </b-form-invalid-feedback>
      </div>
    </b-form-row>

    <div role="group">
      <label for="email">E-Mail<span class="mandatory">*</span></label>
      <b-form-input
        id="email"
        v-model="email"
        aria-describedby="input-live-feedback"
        autocomplete="email"
        placeholder="E-Mail"
        required
        trim
        type="email"
      ></b-form-input>

      <b-form-invalid-feedback id="input-live-feedback">
        Bitte gib eine gültige E-Mail Adresse an.
      </b-form-invalid-feedback>
    </div>

    <b-form-row>
      <div class="col-md-6" role="group">
        <label for="password">Passwort<span class="mandatory">*</span></label>
        <b-form-input
          id="password"
          v-model="password"
          aria-describedby="input-live-help input-live-feedback"
          autocomplete="new-password"
          pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[!§$%&/()=?|\\{}[\]+#;:.,@€_-])[A-Za-z\d!§$%&/()=?|\\{}[\]+#;:.,@€_-]{6,}$"
          placeholder="Passwort"
          required
          trim
          type="password"
          @input="isPasswordConfirmed"
        ></b-form-input>

        <b-form-text id="input-live-help">
          Das Passwort muss mindestens aus 6 Zeichen, bestehend aus Buchstaben,
          Zahlen und Sonderzeichen (<kbd>!§$%&/()=?|{}[]+#;:.,@€_-</kbd>).
        </b-form-text>

        <b-form-invalid-feedback id="input-live-feedback">
          Wähle ein Passwort, das den Sicherheitsbestimmungen entspricht.
        </b-form-invalid-feedback>
      </div>

      <div class="col-md-6" role="group">
        <label for="password-confirm"
          >Passwort bestätigen<span class="mandatory">*</span></label
        >
        <b-form-input
          id="password-confirm"
          ref="passwordConfirmation"
          v-model="passwordConfirmation"
          aria-describedby="input-live-feedback"
          autocomplete="new-password"
          placeholder="Passwort bestätigen"
          required
          trim
          type="password"
          @input="isPasswordConfirmed"
        ></b-form-input>

        <b-form-invalid-feedback id="input-live-feedback">
          <template v-if="!passwordConfirmationErrorMessage">
            Bitte bestätige das Passwort.
          </template>
          <template v-else>
            {{ passwordConfirmationErrorMessage }}
          </template>
        </b-form-invalid-feedback>
      </div>
    </b-form-row>
    <b-button type="submit" variant="primary">Registrieren</b-button>
  </b-form>
</template>

<script>
export default {
  name: "RegistrationForm",
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      passwordConfirmation: "",
      passwordConfirmationErrorMessage: "",
    };
  },
  methods: {
    onSubmit(event) {
      if (!this.$refs.form.checkValidity() || !this.isPasswordConfirmed()) {
        event.preventDefault();
        event.stopPropagation();
      }
      this.$refs.form.classList.add("was-validated");
    },
    isPasswordConfirmed() {
      if (this.password !== this.passwordConfirmation) {
        this.passwordConfirmationErrorMessage =
          "Prüfe, ob deine Passwörter übereinstimmen.";
        this.$refs.passwordConfirmation.setCustomValidity(
          "Passwords do not match."
        );
        return false;
      }

      if (
        !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[!§$%&/()=?|\\{}[\]+#;:.,@€_-])[A-Za-z\d!§$%&/()=?|\\{}[\]+#;:.,@€_-]{6,}$/.test(
          this.passwordConfirmation
        )
      ) {
        this.passwordConfirmationErrorMessage =
          "Wähle ein Passwort, das den Sicherheitsbestimmungen entspricht.";
        this.$refs.passwordConfirmation.setCustomValidity(
          "Password does not match criteria."
        );
        return false;
      }

      this.$refs.passwordConfirmation.setCustomValidity("");
      return true;
    },
  },
};
</script>

<style scoped></style>
