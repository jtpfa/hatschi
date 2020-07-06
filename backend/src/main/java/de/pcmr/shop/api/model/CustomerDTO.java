package de.pcmr.shop.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerDTO {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!§$%&/()=?|\\\\\\{}\\[\\]+#;:.,@€_-])[A-Za-z\\d!§$%&/()=?|\\\\\\{}\\[\\]+#;:.,@€_-]{6,}$";

    @NotNull(message = "Keine Email angegeben")
    @Size(min = 4, max = 100, message = "Email muss zwischen {min} und {max} Zeichen lang sein")
    @Email(message = "Keine valide Email angegeben")
    private String email;
    @NotNull(message = "Kein Vorname angegeben")
    @Size(min = 2, max = 50, message = "Vorname muss zwischen {min} und {max} Zeichen lang sein")
    private String firstName;
    @NotNull(message = "Kein Nachname angegeben")
    @Size(min = 2, max = 50, message = "Nachname muss zwischen {min} und {max} Zeichen lang sein")
    private String lastName;
    @NotNull(message = "Kein Passwort angegeben")
    @Size(min = 6, max = 120, message = "Passwort muss zwischen {min} und {max} Zeichen lang sein")
    @Pattern(regexp = PASSWORD_REGEX)
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
