package de.pcmr.shop.domain;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum CustomerRoleEnum {
    CUSTOMER("customer"),
    EMPLOYEE("employee"),
    ADMIN("admin");

    private String string;

    CustomerRoleEnum(String string) {
        this.string = string;
    }

    public String toString() {
        return string;
    }

    public static CustomerRoleEnum getFromString(String string) {
        for (CustomerRoleEnum customerRoleEnum : values()) {
            if (string.equals(customerRoleEnum.string)) {
                return customerRoleEnum;
            }
        } return null;
    }

    public static List<CustomerRoleEnum> getFromStringList(List<String> strings) {
        return strings.stream().map(CustomerRoleEnum::getFromString).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
