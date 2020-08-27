package de.pcmr.shop.builder;

import de.pcmr.shop.domain.AddressEntity;
import de.pcmr.shop.domain.CustomerEntity;

import java.util.Date;

public final class AddressEntityBuilder {
    private String firstName;
    private String lastName;
    private String address;
    private String additionalAddress;
    private String zip;
    private String city;
    private String country;
    private Boolean active = true;
    private CustomerEntity customer;
    private Long id;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;

    private AddressEntityBuilder() {
    }

    public static AddressEntityBuilder anAddressEntity() {
        return new AddressEntityBuilder();
    }

    public AddressEntityBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AddressEntityBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AddressEntityBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public AddressEntityBuilder withAdditionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
        return this;
    }

    public AddressEntityBuilder withZip(String zip) {
        this.zip = zip;
        return this;
    }

    public AddressEntityBuilder withCity(String city) {
        this.city = city;
        return this;
    }

    public AddressEntityBuilder withCountry(String country) {
        this.country = country;
        return this;
    }

    public AddressEntityBuilder withActive(Boolean active) {
        this.active = active;
        return this;
    }

    public AddressEntityBuilder withCustomer(CustomerEntity customer) {
        this.customer = customer;
        return this;
    }

    public AddressEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AddressEntityBuilder withCreated(Date created) {
        this.created = created;
        return this;
    }

    public AddressEntityBuilder withUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    public AddressEntityBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public AddressEntityBuilder withLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public AddressEntity build() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setFirstName(firstName);
        addressEntity.setLastName(lastName);
        addressEntity.setAddress(address);
        addressEntity.setAdditionalAddress(additionalAddress);
        addressEntity.setZip(zip);
        addressEntity.setCity(city);
        addressEntity.setCountry(country);
        addressEntity.setActive(active);
        addressEntity.setCustomer(customer);
        addressEntity.setId(id);
        addressEntity.setCreated(created);
        addressEntity.setUpdated(updated);
        addressEntity.setCreatedBy(createdBy);
        addressEntity.setLastModifiedBy(lastModifiedBy);
        return addressEntity;
    }
}
