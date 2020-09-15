package de.pcmr.shop.builder;

import de.pcmr.shop.domain.AddressEntity;
import de.pcmr.shop.domain.CustomerEntity;
import de.pcmr.shop.domain.OrderEntity;

import java.util.Date;
import java.util.List;

/**
 * @author Fynn Lohse
 */

public final class CustomerEntityBuilder {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<OrderEntity> orders;
    private List<AddressEntity> addresses;
    private Long id;
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

    public CustomerEntityBuilder withOrders(List<OrderEntity> orders) {
        this.orders = orders;
        return this;
    }

    public CustomerEntityBuilder withAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
        return this;
    }

    public CustomerEntityBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public CustomerEntityBuilder withCreated(Date created) {
        this.created = created;
        return this;
    }

    public CustomerEntityBuilder withUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    public CustomerEntityBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CustomerEntityBuilder withLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public CustomerEntity build() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(email);
        customerEntity.setFirstName(firstName);
        customerEntity.setLastName(lastName);
        customerEntity.setPassword(password);
        customerEntity.setOrders(orders);
        customerEntity.setAddresses(addresses);
        customerEntity.setId(id);
        customerEntity.setCreated(created);
        customerEntity.setUpdated(updated);
        customerEntity.setCreatedBy(createdBy);
        customerEntity.setLastModifiedBy(lastModifiedBy);
        return customerEntity;
    }
}
