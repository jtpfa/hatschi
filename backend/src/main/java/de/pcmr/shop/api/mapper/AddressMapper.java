package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.AddressCreationDTO;
import de.pcmr.shop.api.model.AddressDTO;
import de.pcmr.shop.domain.AddressEntity;
import de.pcmr.shop.util.ValidationUtils;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for mapping DTOs from or to AddressEntities
 *
 * @author Fynn Lohse
 */

public class AddressMapper {
    private AddressMapper() {
        throw new IllegalStateException();
    }

    /**
     * Method maps AddressCreationDTO to AddressEntity
     *
     * @param addressCreationDTO AddressCreationDTO
     * @return AddressEntity
     */

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

    /**
     * Method maps AddressCreationDTO to AddressEntity with ID
     *
     * @param addressCreationDTO AddressCreationDTO
     * @param id ID
     * @return AddressEntity with ID
     */

    public static AddressEntity mapToAddressEntity(AddressCreationDTO addressCreationDTO, Long id) {
        AddressEntity addressEntity = mapToAddressEntity(addressCreationDTO);
        addressEntity.setId(id);
        return addressEntity;
    }

    /**
     * Method map List of AddressEntities to List of AddressDTOs
     *
     * @param addressEntities List of AddressEntities
     * @return List of AddressDTOs
     */

    public static List<AddressDTO> mapToDTOList(List<AddressEntity> addressEntities) {
        return addressEntities.stream().map(AddressMapper::mapToDTO).collect(Collectors.toList());
    }

    private static AddressDTO mapToDTO(AddressEntity addressEntity) {
        ModelMapper modelMapper = new ModelMapper();
        AddressDTO addressDTO = modelMapper.map(addressEntity, AddressDTO.class);

        return addressDTO;
    }
}
