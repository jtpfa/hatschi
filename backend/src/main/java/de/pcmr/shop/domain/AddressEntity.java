package de.pcmr.shop.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

import static de.pcmr.shop.domain.AbstractEntity.TABLE_PREFIX;

@Entity
@Table(name = TABLE_PREFIX + "addresses")
public class AddressEntity extends AbstractEntity {
    @Column(nullable = false, length = 50)
    @Size(min = 1, max = 50, message = "Vorname muss zwischen {min} und {max} Zeichen lang sein")
    private String firstName;

    @Column(nullable = false)
    @Size(min = 1, max = 50, message = "Nachname muss zwischen {min} und {max} Zeichen lang sein")
    private String lastName;

    @Column(nullable = false, length = 255)
    @Size(min = 1, max = 255, message = "Adresse muss zwischen {min} und {max} Zeichen lang sein")
    private String address;

    @Column(length = 255)
    @Size(max = 255, message = "Adresszusatz darf maximal {max} Zeichen lang sein")
    private String additionalAddress;

    @Column(nullable = false)
    @Size(min = 1, max = 50, message = "Postleitzahl muss zwischen {min} und {max} Zeichen lang sein")
    private String zip;

    @Column(nullable = false)
    @Size(min = 1, max = 100, message = "Ort muss zwischen {min} und {max} Zeichen lang sein")
    private String city;

    @Column(nullable = false)
    @Size(min = 1, max = 100, message = "Land muss zwischen {min} und {max} Zeichen lang sein")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private CustomerEntity customer;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdditionalAddress() {
        return additionalAddress;
    }

    public void setAdditionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                address.equals(that.address) &&
                Objects.equals(additionalAddress, that.additionalAddress) &&
                zip.equals(that.zip) &&
                city.equals(that.city) &&
                country.equals(that.country) &&
                customer.equals(that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, additionalAddress, zip, city, country, customer);
    }
}
