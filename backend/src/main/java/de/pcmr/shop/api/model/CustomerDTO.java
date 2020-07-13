package de.pcmr.shop.api.model;

import javax.validation.constraints.NotNull;

public class CustomerDTO {
    @NotNull(message = "Keine Email angegeben")
    private String email;

    @NotNull(message = "Kein Vorname angegeben")
    private String firstName;

    @NotNull(message = "Kein Nachname angegeben")
    private String lastName;

    @NotNull(message = "Kein Passwort angegeben")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
