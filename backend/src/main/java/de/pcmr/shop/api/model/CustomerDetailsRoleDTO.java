package de.pcmr.shop.api.model;

import de.pcmr.shop.domain.CustomerRoleEnum;

public class CustomerDetailsRoleDTO extends CustomerDetailsDTO {
    private CustomerRoleEnum customerRoleEnum;

    public CustomerRoleEnum getCustomerRoleEnum() {
        return customerRoleEnum;
    }

    public void setCustomerRoleEnum(CustomerRoleEnum customerRoleEnum) {
        this.customerRoleEnum = customerRoleEnum;
    }
}
