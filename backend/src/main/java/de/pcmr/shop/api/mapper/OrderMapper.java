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

public class OrderMapper {
    private OrderMapper() {
        throw new IllegalStateException();
    }

    public static List<OrderDTO> mapListToDTOList(List<OrderEntity> orderEntities) {
        return orderEntities.stream().map(OrderMapper::mapToDTO).collect(Collectors.toList());
    }

    private static OrderDTO mapToDTO(OrderEntity orderEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

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

    private static void checkIfAddressExists(Long addressId, AddressRepository addressRepository) throws NoAddressFoundException {
        if (!addressRepository.existsById(addressId)) {
            throw new NoAddressFoundException(addressId);
        }
    }
}
