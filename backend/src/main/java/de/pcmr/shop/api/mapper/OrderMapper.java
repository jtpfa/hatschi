package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.api.model.OrderDTO;
import de.pcmr.shop.domain.OrderEntity;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.repository.AddressRepository;
import de.pcmr.shop.repository.ArticleRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for mapping DTOs from or to OrderEntities
 *
 * @author Fynn Lohse
 */

public class OrderMapper {
    private OrderMapper() {
        throw new IllegalStateException();
    }

    /**
     * Method maps List of OrderEntities to List of OrderDTOs.
     *
     * @param orderEntities List of OrderEntities
     * @return List of OrderDTOs
     */

    public static List<OrderDTO> mapListToDTOList(List<OrderEntity> orderEntities) {
        return orderEntities.stream().map(OrderMapper::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Method maps OrderCreationDTO to OrderEntity.
     *
     * @param orderCreationDTO OrderCreationDTO
     * @param articleRepository ArticleRepository
     * @param addressRepository AddressRepository
     * @return OrderEntity
     * @throws NoArticleFoundException if no ArticleEntity is found to given ID
     * @throws NoAddressFoundException if no AddressEntity is found to given ID
     */

    public static OrderEntity mapCreationDTOToEntity(OrderCreationDTO orderCreationDTO, ArticleRepository articleRepository, AddressRepository addressRepository) throws NoArticleFoundException, NoAddressFoundException {
        OrderEntity orderEntity = new OrderEntity();
        checkIfAddressExists(orderCreationDTO.getInvoiceAddressId(), addressRepository);
        checkIfAddressExists(orderCreationDTO.getShippingAddressId(), addressRepository);

        orderEntity.setInvoiceAddress(addressRepository.findById(orderCreationDTO.getInvoiceAddressId()).get());
        orderEntity.setShippingAddress(addressRepository.findById(orderCreationDTO.getShippingAddressId()).get());
        orderEntity.setPaymentMethod(orderCreationDTO.getPaymentMethod());
        orderEntity.setShippingMethod(orderCreationDTO.getShippingMethod());
        orderEntity.setOrderItems(OrderItemMapper.mapListToOrderItemEntityList(orderCreationDTO.getOrderItems(), articleRepository));

        return orderEntity;
    }

    private static OrderDTO mapToDTO(OrderEntity orderEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    private static void checkIfAddressExists(Long addressId, AddressRepository addressRepository) throws NoAddressFoundException {
        if (!addressRepository.existsById(addressId)) {
            throw new NoAddressFoundException(addressId);
        }
    }
}
