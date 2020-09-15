package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.AddressCreationDTO;
import de.pcmr.shop.api.model.AddressDTO;
import de.pcmr.shop.builder.AddressEntityBuilder;
import de.pcmr.shop.domain.AddressEntity;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Fynn Lohse
 */

class AddressMapperUnitTest {

    private AddressEntity resultAddressEntity;

    private List<AddressEntity> addressEntities;
    private List<AddressDTO> addressDTOs;
    private AddressCreationDTO addressCreationDTO;

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    @Test
    void testMapToAddressEntity() {
        given.anAddressCreationDTO();
        when.anAddressCreationDTOIsMappedToEntity();
        then.theEntityEquals(addressCreationDTO, resultAddressEntity);
    }

    @Test
    void testMapToAddressEntityFailedHTML() {
        given.anAddressCreationDTOWithHTML();
        assertThrows(ValidationException.class, when::anAddressCreationDTOIsMappedToEntity);
    }

    @Test
    void testMapToAddressEntityWithId() {
        given.anAddressCreationDTO();
        when.anAddressCreationDTOIsMappedToEntityWithId(5L);
        then.theEntityEqualsWithId(addressCreationDTO, 5L, resultAddressEntity);
    }

    @Test
    void mapToDTOList() {
        given.anAddressEntityList();
        when.anAddressEntityListIsMappedToDtoList();
        then.theDtoListEquals(addressEntities, addressDTOs);
    }

    class Given {
        void anAddressCreationDTO() {
            addressCreationDTO = new AddressCreationDTO();
            addressCreationDTO.setAdditionalAddress("1. OG");
            addressCreationDTO.setAddress("Hauptstaße 12");
            addressCreationDTO.setCity("Berlin");
            addressCreationDTO.setCountry("Germany");
            addressCreationDTO.setFirstName("Petra");
            addressCreationDTO.setLastName("Schmidt");
            addressCreationDTO.setZip("12345");
        }

        void anAddressEntityList() {
            AddressEntity addressEntity1 = AddressEntityBuilder.anAddressEntity()
                    .withId(1L)
                    .withAdditionalAddress("1. OG")
                    .withAddress("Hauptstraße 12")
                    .withCity("Berlin")
                    .withCountry("Germany")
                    .withFirstName("Peta")
                    .withLastName("Schmidt")
                    .withZip("12345")
                    .build();

            AddressEntity addressEntity2 = AddressEntityBuilder.anAddressEntity()
                    .withId(2L)
                    .withAdditionalAddress("2. OG")
                    .withAddress("Teststraße 5")
                    .withCity("Hamburg")
                    .withCountry("Deutschland")
                    .withFirstName("Peter")
                    .withLastName("Meyer")
                    .withZip("54321")
                    .build();

            AddressEntity addressEntity3 = AddressEntityBuilder.anAddressEntity()
                    .withId(3L)
                    .withAddress("Tesla Str. 1")
                    .withCity("Grünheide (Mark)")
                    .withCountry("Germany")
                    .withFirstName("Elon")
                    .withLastName("Musk")
                    .withZip("15537")
                    .build();

            addressEntities = List.of(addressEntity1, addressEntity2, addressEntity3);
        }

        void anAddressCreationDTOWithHTML() {
            addressCreationDTO = new AddressCreationDTO();
            addressCreationDTO.setAdditionalAddress("1. OG");
            addressCreationDTO.setAddress("Hauptstaße 12");
            addressCreationDTO.setCity("<script> window.alert('xss'); </script>");
            addressCreationDTO.setCountry("Germany");
            addressCreationDTO.setFirstName("Petra");
            addressCreationDTO.setLastName("Schmidt");
            addressCreationDTO.setZip("12345");
        }
    }

    class When {
        void anAddressCreationDTOIsMappedToEntity() {
            resultAddressEntity = AddressMapper.mapToAddressEntity(addressCreationDTO);
        }

        void anAddressCreationDTOIsMappedToEntityWithId(Long id) {
            resultAddressEntity = AddressMapper.mapToAddressEntity(addressCreationDTO, id);
        }

        void anAddressEntityListIsMappedToDtoList() {
            addressDTOs = AddressMapper.mapToDTOList(addressEntities);
        }
    }

    class Then {
        void theEntityEquals(AddressCreationDTO addressCreationDTO, AddressEntity addressEntity) {
            assertEquals(addressCreationDTO.getAdditionalAddress(), addressEntity.getAdditionalAddress());
            assertEquals(addressCreationDTO.getAddress(), addressEntity.getAddress());
            assertEquals(addressCreationDTO.getCity(), addressEntity.getCity());
            assertEquals(addressCreationDTO.getCountry(), addressEntity.getCountry());
            assertEquals(addressCreationDTO.getFirstName(), addressEntity.getFirstName());
            assertEquals(addressCreationDTO.getLastName(), addressEntity.getLastName());
            assertEquals(addressCreationDTO.getZip(), addressEntity.getZip());
        }

        void theEntityEqualsWithId(AddressCreationDTO addressCreationDTO, Long id, AddressEntity addressEntity) {
            assertEquals(addressCreationDTO.getAdditionalAddress(), addressEntity.getAdditionalAddress());
            assertEquals(addressCreationDTO.getAddress(), addressEntity.getAddress());
            assertEquals(addressCreationDTO.getCity(), addressEntity.getCity());
            assertEquals(addressCreationDTO.getCountry(), addressEntity.getCountry());
            assertEquals(addressCreationDTO.getFirstName(), addressEntity.getFirstName());
            assertEquals(addressCreationDTO.getLastName(), addressEntity.getLastName());
            assertEquals(addressCreationDTO.getZip(), addressEntity.getZip());
            assertEquals(id, addressEntity.getId());
        }

        void theDtoListEquals(List<AddressEntity> addressEntities, List<AddressDTO> addressDTOs) {
            assertEquals(addressEntities.size(), addressDTOs.size());
            for (int i=0; i<addressDTOs.size(); i++) {
                assertEquals(addressEntities.get(i).getId(), addressDTOs.get(i).getId());
                assertEquals(addressEntities.get(i).getAddress(), addressDTOs.get(i).getAddress());
                assertEquals(addressEntities.get(i).getAdditionalAddress(), addressDTOs.get(i).getAdditionalAddress());
                assertEquals(addressEntities.get(i).getCity(), addressDTOs.get(i).getCity());
                assertEquals(addressEntities.get(i).getCountry(), addressDTOs.get(i).getCountry());
                assertEquals(addressEntities.get(i).getFirstName(), addressDTOs.get(i).getFirstName());
                assertEquals(addressEntities.get(i).getLastName(), addressDTOs.get(i).getLastName());
                assertEquals(addressEntities.get(i).getZip(), addressDTOs.get(i).getZip());
            }
        }
    }
}