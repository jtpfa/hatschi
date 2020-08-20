package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.AddressDTO;
import de.pcmr.shop.domain.AddressEntity;
import de.pcmr.shop.util.ValidationUtils;

public class AddressMapper {
    private AddressMapper() {
        throw new IllegalStateException();
    }

    public static AddressEntity mapToAddressEntity(AddressDTO addressDTO) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setFirstName(ValidationUtils.validateNoHtml(addressDTO.getFirstName()));
        addressEntity.setLastName(ValidationUtils.validateNoHtml(addressDTO.getLastName()));
        addressEntity.setAddress(ValidationUtils.validateNoHtml(addressDTO.getAddress()));
        addressEntity.setCity(ValidationUtils.validateNoHtml(addressDTO.getCity()));
        addressEntity.setZip(ValidationUtils.validateNoHtml(addressDTO.getZip()));
        addressEntity.setAdditionalAddress(ValidationUtils.validateNoHtml(addressDTO.getAdditionalAddress()));
        addressEntity.setCountry(ValidationUtils.validateNoHtml(addressDTO.getCountry()));

        return addressEntity;
    }
}
