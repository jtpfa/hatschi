package de.pcmr.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static de.pcmr.shop.domain.AbstractEntity.TABLE_PREFIX;

@Entity
@Table(name = TABLE_PREFIX + "customers")
public class CustomerEntity extends AbstractEntity {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!§$%&/()=?|\\\\{}\\[\\]+#;:.,@€_-])[A-Za-z\\d!§$%&/()=?|\\\\{}\\[\\]+#;:.,@€_-]{6,}$";

    @Column(nullable = false, unique = true, length = 100)
    @Size(min = 4, max = 100, message = "Email muss zwischen {min} und {max} Zeichen lang sein")
    @Email(message = "Keine valide Email angegeben")
    private String email;

    @Column(nullable = false, length = 50)
    @Size(min = 1, max = 50, message = "Vorname muss zwischen {min} und {max} Zeichen lang sein")
    private String firstName;

    @Column(nullable = false, length = 50)
    @Size(min = 1, max = 50, message = "Nachname muss zwischen {min} und {max} Zeichen lang sein")
    private String lastName;

    @Transient
    @Size(min = 6, max = 120, message = "Passwort muss zwischen {min} und {max} Zeichen lang sein")
    @Pattern(regexp = PASSWORD_REGEX, message = "Passwort entspricht nicht den Passwortregeln")
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
