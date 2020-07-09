package de.pcmr.shop.builder;

import de.pcmr.shop.domain.CustomerEntity;

import java.util.Date;

public final class CustomerEntityBuilder {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private long id;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;

    private CustomerEntityBuilder() {
    }

    public static CustomerEntityBuilder aCustomerEntity() {
        return new CustomerEntityBuilder();
    }

    public CustomerEntityBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerEntityBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerEntityBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerEntityBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public CustomerEntityBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public CustomerEntity build() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(email);
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName(lastName);
        customerEntity.setPassword(password);
        customerEntity.setId(id);
        return customerEntity;
    }
}
