package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.AddressCreationDTO;
import de.pcmr.shop.api.model.AddressDTO;
import de.pcmr.shop.domain.AddressEntity;
import de.pcmr.shop.util.ValidationUtils;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AddressMapper {
    private AddressMapper() {
        throw new IllegalStateException();
    }

    public static AddressEntity mapToAddressEntity(AddressCreationDTO addressCreationDTO) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setFirstName(ValidationUtils.validateNoHtml(addressCreationDTO.getFirstName()).trim());
        addressEntity.setLastName(ValidationUtils.validateNoHtml(addressCreationDTO.getLastName()).trim());
        addressEntity.setAddress(ValidationUtils.validateNoHtml(addressCreationDTO.getAddress()).trim());
        addressEntity.setCity(ValidationUtils.validateNoHtml(addressCreationDTO.getCity()).trim());
        addressEntity.setZip(ValidationUtils.validateNoHtml(addressCreationDTO.getZip()).trim());
        addressEntity.setAdditionalAddress(ValidationUtils.validateNoHtml(addressCreationDTO.getAdditionalAddress()).trim());
        addressEntity.setCountry(ValidationUtils.validateNoHtml(addressCreationDTO.getCountry()).trim());

        return addressEntity;
    }

    public static AddressEntity mapToAddressEntity(AddressCreationDTO addressCreationDTO, Long id) {
        AddressEntity addressEntity = mapToAddressEntity(addressCreationDTO);
        addressEntity.setId(id);
        return addressEntity;
    }

    public static List<AddressDTO> mapToDTOList(List<AddressEntity> addressEntities) {
        return addressEntities.stream().map(AddressMapper::mapToDTO).collect(Collectors.toList());
    }

    private static AddressDTO mapToDTO(AddressEntity addressEntity) {
        ModelMapper modelMapper = new ModelMapper();
        AddressDTO addressDTO = modelMapper.map(addressEntity, AddressDTO.class);

        return addressDTO;
    }
}
