package de.pcmr.shop.api.mapper;

import de.pcmr.shop.api.model.OrderCreationDTO;
import de.pcmr.shop.api.model.OrderDTO;
import de.pcmr.shop.api.model.OrderItemDTO;
import de.pcmr.shop.builder.*;
import de.pcmr.shop.domain.*;
import de.pcmr.shop.exception.NoAddressFoundException;
import de.pcmr.shop.exception.NoArticleFoundException;
import de.pcmr.shop.repository.AddressRepository;
import de.pcmr.shop.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Fynn Lohse
 */

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    private List<OrderEntity> orderEntities;
    private OrderCreationDTO orderCreationDTO;
    private List<OrderDTO> mappingResultList;
    private OrderEntity mappingResult;
    private AddressEntity addressEntity;
    private CustomerEntity customerEntity;
    private ArticleEntity articleEntity1;
    private ArticleEntity articleEntity2;
    private ArticleEntity articleEntity3;

    @Mock private ArticleRepository articleRepository;
    @Mock private AddressRepository addressRepository;

    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    @Test
    void mapListToDTOList() {
        given.aCustomerEntity();
        given.anAddressEntity();
        given.articleEntities();
        given.anOrderEntityList();
        when.anOrderEntityListIsMappedToDtoList();
        then.theDtoListEquals(orderEntities, mappingResultList);
    }

    @Test
    void mapCreationDTOToEntity() throws NoArticleFoundException, NoAddressFoundException {
        given.aCustomerEntity();
        given.anAddressEntity();
        given.articleEntities();
        given.aMockConfig();
        given.anOrderCreationDTO();
        when.anOrderCreationDtoIsMappedToEntity();
        then.theEntityEquals(orderCreationDTO, mappingResult);
    }

    class Given {
        void aMockConfig() {
            when(articleRepository.existsById(anyLong())).thenReturn(true);
            when(articleRepository.findById(1L)).thenReturn(Optional.of(articleEntity1));
            when(articleRepository.findById(2L)).thenReturn(Optional.of(articleEntity2));
            when(articleRepository.findById(3L)).thenReturn(Optional.of(articleEntity3));

            when(addressRepository.existsById(1L)).thenReturn(true);
            when(addressRepository.findById(1L)).thenReturn(Optional.of(addressEntity));
        }

        void anOrderCreationDTO() {
            orderCreationDTO = new OrderCreationDTO();
            orderCreationDTO.setPaymentMethod(PaymentMethodEnum.PAYPAL);
            orderCreationDTO.setShippingMethod(ShippingMethodEnum.EXPRESS);

            OrderItemDTO orderItemDTO1 = new OrderItemDTO();
            orderItemDTO1.setArticleId(1L);
            orderItemDTO1.setQuantity(2);

            OrderItemDTO orderItemDTO2 = new OrderItemDTO();
            orderItemDTO2.setArticleId(2L);
            orderItemDTO2.setQuantity(1);

            OrderItemDTO orderItemDTO3 = new OrderItemDTO();
            orderItemDTO3.setArticleId(3L);
            orderItemDTO3.setQuantity(3);

            orderCreationDTO.setOrderItems(List.of(orderItemDTO1, orderItemDTO2, orderItemDTO3));
            orderCreationDTO.setInvoiceAddressId(1L);
            orderCreationDTO.setShippingAddressId(1L);
        }

        void aCustomerEntity() {
            customerEntity = CustomerEntityBuilder.aCustomerEntity()
                    .withEmail("test@customer")
                    .withFirstName("Peter")
                    .withLastName("Meier")
                    .withPassword("Passwort1!")
                    .build();
        }

        void anAddressEntity() {
            addressEntity = AddressEntityBuilder.anAddressEntity()
                    .withId(1L)
                    .withFirstName("Peter")
                    .withLastName("Meier")
                    .withCountry("Germany")
                    .withCity("Berlin")
                    .withZip("12345")
                    .withAddress("Hauptstraße 13")
                    .withAdditionalAddress("31. OG")
                    .withCustomer(customerEntity)
                    .build();
        }

        void articleEntities() {
            articleEntity1 = ArticleEntityBuilder.anArticleEntity()
                    .withId(1L)
                    .withName("Testartikel 1")
                    .withDescription("Testbeschreibung 1")
                    .withDetails("Testdetails 1")
                    .withStock(10)
                    .withPrice(999)
                    .build();

            articleEntity2 = ArticleEntityBuilder.anArticleEntity()
                    .withId(2L)
                    .withName("Testartikel 2")
                    .withDescription("Testbeschreibung 2")
                    .withDetails("Testdetails 2")
                    .withStock(7)
                    .withPrice(1999)
                    .build();

            articleEntity3 = ArticleEntityBuilder.anArticleEntity()
                    .withId(3L)
                    .withName("Testartikel 3")
                    .withDescription("Testbeschreibung 3")
                    .withDetails("Testdetails 3")
                    .withStock(3)
                    .withPrice(2599)
                    .build();
        }

        void anOrderEntityList() {
            OrderItemEntity orderItemEntity1 = OrderItemEntityBuilder.anOrderItemEntity()
                    .withArticle(articleEntity1)
                    .withQuantity(2)
                    .withPrice(1998)
                    .build();
            OrderItemEntity orderItemEntity2 = OrderItemEntityBuilder.anOrderItemEntity()
                    .withArticle(articleEntity2)
                    .withQuantity(1)
                    .withPrice(1999)
                    .build();
            OrderItemEntity orderItemEntity3 = OrderItemEntityBuilder.anOrderItemEntity()
                    .withArticle(articleEntity3)
                    .withQuantity(1)
                    .withPrice(2599)
                    .build();
            OrderItemEntity orderItemEntity4 = OrderItemEntityBuilder.anOrderItemEntity()
                    .withArticle(articleEntity1)
                    .withQuantity(1)
                    .withPrice(999)
                    .build();

            OrderEntity orderEntity1 = OrderEntityBuilder.anOrderEntity()
                    .withOrderItems(List.of(orderItemEntity3))
                    .withOrderDate(new Date())
                    .withCustomer(customerEntity)
                    .withInvoiceAddress(addressEntity)
                    .withShippingAddress(addressEntity)
                    .withShippingMethod(ShippingMethodEnum.EXPRESS)
                    .withPaid(true)
                    .withOrderStatus(OrderStatusEnum.OPEN)
                    .withPaymentMethod(PaymentMethodEnum.INVOICE)
                    .build();

            OrderEntity orderEntity2 = OrderEntityBuilder.anOrderEntity()
                    .withOrderItems(List.of(orderItemEntity4))
                    .withOrderDate(new Date())
                    .withCustomer(customerEntity)
                    .withInvoiceAddress(addressEntity)
                    .withShippingAddress(addressEntity)
                    .withShippingMethod(ShippingMethodEnum.DEFAULT)
                    .withPaid(true)
                    .withOrderStatus(OrderStatusEnum.OPEN)
                    .withPaymentMethod(PaymentMethodEnum.CREDIT_CARD)
                    .build();

            OrderEntity orderEntity3 = OrderEntityBuilder.anOrderEntity()
                    .withOrderItems(List.of(orderItemEntity1, orderItemEntity2))
                    .withOrderDate(new Date())
                    .withCustomer(customerEntity)
                    .withInvoiceAddress(addressEntity)
                    .withShippingAddress(addressEntity)
                    .withShippingMethod(ShippingMethodEnum.EXPRESS)
                    .withPaid(false)
                    .withOrderStatus(OrderStatusEnum.OPEN)
                    .withPaymentMethod(PaymentMethodEnum.PAYPAL)
                    .build();

            orderEntities = List.of(orderEntity1, orderEntity2, orderEntity3);
        }
    }

    class When {
        void anOrderEntityListIsMappedToDtoList() {
            mappingResultList = OrderMapper.mapListToDTOList(orderEntities);
        }

        void anOrderCreationDtoIsMappedToEntity() throws NoArticleFoundException, NoAddressFoundException {
            mappingResult = OrderMapper.mapCreationDTOToEntity(orderCreationDTO, articleRepository, addressRepository);
        }
    }

    class Then {
        void theDtoListEquals(List<OrderEntity> orderEntities, List<OrderDTO> orderDTOs) {
            assertEquals(orderEntities.size(), orderDTOs.size());
            for (int i=0; i<orderEntities.size(); i++) {
                OrderEntity orderEntity = orderEntities.get(i);
                OrderDTO orderDTO = orderDTOs.get(i);

                assertEquals(orderEntity.getInvoiceAddress().getFirstName(), orderDTO.getInvoiceAddress().getFirstName());
                assertEquals(orderEntity.getInvoiceAddress().getLastName(), orderDTO.getInvoiceAddress().getLastName());
                assertEquals(orderEntity.getInvoiceAddress().getCity(), orderDTO.getInvoiceAddress().getCity());
                assertEquals(orderEntity.getInvoiceAddress().getCountry(), orderDTO.getInvoiceAddress().getCountry());
                assertEquals(orderEntity.getInvoiceAddress().getZip(), orderDTO.getInvoiceAddress().getZip());
                assertEquals(orderEntity.getInvoiceAddress().getAddress(), orderDTO.getInvoiceAddress().getAddress());
                assertEquals(orderEntity.getInvoiceAddress().getAdditionalAddress(), orderDTO.getInvoiceAddress().getAdditionalAddress());

                assertEquals(orderEntity.getShippingAddress().getFirstName(), orderDTO.getShippingAddress().getFirstName());
                assertEquals(orderEntity.getShippingAddress().getLastName(), orderDTO.getShippingAddress().getLastName());
                assertEquals(orderEntity.getShippingAddress().getCity(), orderDTO.getShippingAddress().getCity());
                assertEquals(orderEntity.getShippingAddress().getCountry(), orderDTO.getShippingAddress().getCountry());
                assertEquals(orderEntity.getShippingAddress().getZip(), orderDTO.getShippingAddress().getZip());
                assertEquals(orderEntity.getShippingAddress().getAddress(), orderDTO.getShippingAddress().getAddress());
                assertEquals(orderEntity.getShippingAddress().getAdditionalAddress(), orderDTO.getShippingAddress().getAdditionalAddress());

                assertEquals(orderEntity.getCustomer().getEmail(), orderDTO.getCustomerEmail());
                assertEquals(orderEntity.getOrderDate(), orderDTO.getOrderDate());
                assertEquals(orderEntity.getOrderStatus(), orderDTO.getOrderStatus());
                assertEquals(orderEntity.getPaymentMethod(), orderDTO.getPaymentMethod());
                assertEquals(orderEntity.getShippingMethod(), orderDTO.getShippingMethod());

                assertEquals(orderEntity.getOrderItems().size(), orderDTO.getOrderItems().size());
            }
        }

        void theEntityEquals(OrderCreationDTO orderCreationDTO, OrderEntity orderEntity) {
            assertEquals(orderCreationDTO.getInvoiceAddressId(), orderEntity.getInvoiceAddress().getId());
            assertEquals(orderCreationDTO.getShippingAddressId(), orderEntity.getShippingAddress().getId());
            assertEquals(orderCreationDTO.getPaymentMethod(), orderEntity.getPaymentMethod());
            assertEquals(orderCreationDTO.getShippingMethod(), orderEntity.getShippingMethod());

            List<OrderItemEntity> orderItems = orderEntity.getOrderItems();
            List<OrderItemDTO> orderItemDTOs = orderCreationDTO.getOrderItems();

            assertEquals(orderItems.size(), orderItemDTOs.size());
            for (int i=0; i<orderItems.size(); i++) {
                assertEquals(orderItemDTOs.get(i).getQuantity(), orderItems.get(i).getQuantity());
                assertEquals(orderItemDTOs.get(i).getArticleId(), orderItems.get(i).getArticle().getId());
            }
        }
    }
}