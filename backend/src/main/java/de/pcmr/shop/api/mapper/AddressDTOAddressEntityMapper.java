package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.AddressDTO;
import de.pcmr.shop.domain.AddressEntity;

public class AddressDTOAddressEntityMapper {
    public static AddressEntity mapToAddressEntity(AddressDTO addressDTO) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setFirstName(addressDTO.getFirstName());
        addressEntity.setLastName(addressDTO.getLastName());
        addressEntity.setAddress(addressDTO.getAddress());
        addressEntity.setCity(addressDTO.getCity());
        addressEntity.setZip(addressDTO.getZip());
        addressEntity.setAdditionalAddress(addressDTO.getAdditionalAddress());
        addressEntity.setCountry(addressDTO.getCountry());

        return addressEntity;
    }
}
