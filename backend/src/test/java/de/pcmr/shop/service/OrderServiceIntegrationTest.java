package de.pcmr.shop.service;

import de.pcmr.shop.AbstractIntegrationTest;
import de.pcmr.shop.builder.*;
import de.pcmr.shop.domain.*;
import de.pcmr.shop.exception.DuplicateOrderItemsException;
import de.pcmr.shop.exception.NoCustomerFoundException;
import de.pcmr.shop.exception.NotEnoughArticlesOnStockException;
import de.pcmr.shop.repository.ArticleRepository;
import de.pcmr.shop.repository.CustomerRepository;
import de.pcmr.shop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceIntegrationTest extends AbstractIntegrationTest {

    private CustomerEntity customerEntity;
    private OrderEntity orderEntity;

    private List<OrderItemEntity> orderItemEntities = new ArrayList<>();
    private List<ArticleEntity> articleEntities = new ArrayList<>();
    private AddressEntity addressEntity;

    private final static String CUSTOMER_EMAIL_A = "test@userA.de";
    private final static String CUSTOMER_FIRSTNAME_A = "TestA";
    private final static String CUSTOMER_LASTNAME_A = "UserA";
    private final static String CUSTOMER_PASSWORD_A = "MyP@ssw0rdA";

    private final CustomerRepository customerRepository;
    private final ArticleRepository articleRepository;
    private final OrderRepository orderRepository;
    private final OrderServiceI orderService;


    private final Given given = new Given();
    private final When when = new When();
    private final Then then = new Then();

    @Autowired
    OrderServiceIntegrationTest(CustomerRepository customerRepository, ArticleRepository articleRepository, OrderRepository orderRepository, OrderServiceI orderService) {
        super();
        this.customerRepository = customerRepository;
        this.articleRepository = articleRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @BeforeEach
    void cleanup() {
        orderItemEntities = new ArrayList<>();
        articleEntities = new ArrayList<>();
    }

    @Test
    void testProcessOrder() throws NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException {
        given.aCustomerEntityInDatabaseWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        given.anArticleEntityInDatabaseWith("Testartikel 1", "Testbeschreibung 1", "Testdetails 1", 5, 150);
        given.anArticleEntityInDatabaseWith("Testartikel 2", "Testbeschreibung 2", "Testdetails 2", 3, 999);

        given.anAddressEntity();
        given.anOrderItemEntityWith(articleEntities.get(0), 1);
        given.anOrderItemEntityWith(articleEntities.get(1), 3);
        given.anOrderEntityWith(addressEntity, addressEntity, PaymentMethodEnum.CREDIT_CARD, ShippingMethodEnum.DEFAULT, orderItemEntities.get(0), orderItemEntities.get(1));

        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        when.anOrderIsProcessed(orderEntity);

        then.numberOfOrdersInDatabaseAre(1);
        then.generatedAttributesAreNotNull();
        then.theStockIsOfTheArticleIs(articleEntities.get(0), 4);
        then.theStockIsOfTheArticleIs(articleEntities.get(1), 0);
    }

    @Test
    void testProcessOrderNotEnoughArticlesOnStock() {
        given.aCustomerEntityInDatabaseWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        given.anArticleEntityInDatabaseWith("Testartikel 1", "Testbeschreibung 1", "Testdetails 1", 5, 150);
        given.anArticleEntityInDatabaseWith("Testartikel 2", "Testbeschreibung 2", "Testdetails 2", 3, 999);

        given.anAddressEntity();
        given.anOrderItemEntityWith(articleEntities.get(0), 1);
        given.anOrderItemEntityWith(articleEntities.get(1), 4);
        given.anOrderEntityWith(addressEntity, addressEntity, PaymentMethodEnum.PAYPAL, ShippingMethodEnum.EXPRESS, orderItemEntities.get(0), orderItemEntities.get(1));

        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        assertThrows(NotEnoughArticlesOnStockException.class, () -> when.anOrderIsProcessed(orderEntity));
    }

    @Test
    void testProcessOrderDuplicateOrderItems() {
        given.aCustomerEntityInDatabaseWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        given.anArticleEntityInDatabaseWith("Testartikel 1", "Testbeschreibung 1", "Testdetails 1", 5, 150);
        given.anArticleEntityInDatabaseWith("Testartikel 2", "Testbeschreibung 2", "Testdetails 2", 3, 999);

        given.anAddressEntity();
        given.anOrderItemEntityWith(articleEntities.get(0), 1);
        given.anOrderItemEntityWith(articleEntities.get(1), 4);
        given.anOrderItemEntityWith(articleEntities.get(0), 1);
        given.anOrderEntityWith(addressEntity, addressEntity, PaymentMethodEnum.INVOICE, ShippingMethodEnum.DEFAULT, orderItemEntities.get(0), orderItemEntities.get(1), orderItemEntities.get(2));

        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        assertThrows(DuplicateOrderItemsException.class, () -> when.anOrderIsProcessed(orderEntity));
    }

    @Test
    void testProcessOrderNoCustomerFound() {
        given.aCustomerEntityInDatabaseWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        given.anArticleEntityInDatabaseWith("Testartikel 1", "Testbeschreibung 1", "Testdetails 1", 5, 150);
        given.anArticleEntityInDatabaseWith("Testartikel 2", "Testbeschreibung 2", "Testdetails 2", 3, 999);

        given.anAddressEntity();
        given.anOrderItemEntityWith(articleEntities.get(0), 1);
        given.anOrderItemEntityWith(articleEntities.get(1), 3);
        given.anOrderEntityWith(addressEntity, addressEntity, PaymentMethodEnum.CREDIT_CARD, ShippingMethodEnum.DEFAULT, orderItemEntities.get(0), orderItemEntities.get(1));

        assertThrows(NoCustomerFoundException.class, () -> when.anOrderIsProcessed(orderEntity));
    }

    @Test
    void testProcessOrderNoOrderItems() throws NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException {
        given.aCustomerEntityInDatabaseWith(CUSTOMER_EMAIL_A, CUSTOMER_FIRSTNAME_A, CUSTOMER_LASTNAME_A, CUSTOMER_PASSWORD_A);
        given.anArticleEntityInDatabaseWith("Testartikel 1", "Testbeschreibung 1", "Testdetails 1", 5, 150);
        given.anArticleEntityInDatabaseWith("Testartikel 2", "Testbeschreibung 2", "Testdetails 2", 3, 999);

        given.anAddressEntity();
        given.anOrderEntityWith(addressEntity, addressEntity, PaymentMethodEnum.CREDIT_CARD, ShippingMethodEnum.DEFAULT);

        when.aRegisteredCustomerIsAuthenticated(CUSTOMER_EMAIL_A, CUSTOMER_PASSWORD_A);
        assertThrows(ConstraintViolationException.class, () -> when.anOrderIsProcessed(orderEntity));
    }

    class Given {
        void aCustomerEntityInDatabaseWith(String email, String firstname, String lastname, String password) {
            customerEntity = CustomerEntityBuilder.aCustomerEntity()
                    .withEmail(email)
                    .withFirstName(firstname)
                    .withLastName(lastname)
                    .withPassword(password)
                    .build();
            customerEntity = customerRepository.save(customerEntity);
        }

        void anArticleEntityInDatabaseWith(String name, String description, String details, int stock, int price) {
            ArticleEntity articleEntity = ArticleEntityBuilder.anArticleEntity()
                    .withName(name)
                    .withDescription(description)
                    .withDetails(details)
                    .withStock(stock)
                    .withPrice(price)
                    .build();
            articleEntities.add(articleRepository.save(articleEntity));
        }

        void anOrderEntityWith(AddressEntity invoiceAddress, AddressEntity shippingAddress, PaymentMethodEnum paymentMethod, ShippingMethodEnum shippingMethod, OrderItemEntity... orderItemEntities) {
            orderEntity = OrderEntityBuilder.anOrderEntity()
                    .withOrderItems(List.of(orderItemEntities))
                    .withInvoiceAddress(invoiceAddress)
                    .withShippingAddress(shippingAddress)
                    .withShippingMethod(shippingMethod)
                    .withPaymentMethod(paymentMethod)
                    .build();
        }

        void anAddressEntity() {
            addressEntity = AddressEntityBuilder.anAddressEntity()
                    .withAddress("Teststraße 21")
                    .withAdditionalAddress("3. Stock")
                    .withZip("12345")
                    .withCity("Berlin")
                    .withCountry("Germany")
                    .withFirstName("TestFirst")
                    .withLastName("TestLast")
                    .build();
        }

        void anOrderItemEntityWith(ArticleEntity articleEntity, int quantity) {
            orderItemEntities.add(OrderItemEntityBuilder.anOrderItemEntity()
                    .withArticle(articleEntity)
                    .withQuantity(quantity)
                    .build());
        }
    }

    class When {
        void aRegisteredCustomerIsAuthenticated(String email, String password) {
            Authentication auth = new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
        }

        void anOrderIsProcessed(OrderEntity orderEntity) throws NoCustomerFoundException, NotEnoughArticlesOnStockException, DuplicateOrderItemsException {
            orderService.processOrder(orderEntity, SecurityContextHolder.getContext().getAuthentication());
        }

    }

    class Then {
        void numberOfOrdersInDatabaseAre(int number) {
            assertEquals(number, orderRepository.findAll().size());
        }

        void generatedAttributesAreNotNull() {
            OrderEntity orderEntity = orderRepository.findAll().get(0);
            assertNotNull(orderEntity);
            assertNotNull(orderEntity.getCustomer());
            assertNotNull(orderEntity.getInvoiceAddress());
            assertNotNull(orderEntity.getShippingAddress());
            assertNotNull(orderEntity.getShippingAddress());
            assertNotNull(orderEntity.getOrderDate());
            assertNotNull(orderEntity.getOrderStatus());
        }

        void theStockIsOfTheArticleIs(ArticleEntity articleEntity, int expectedStock) {
            articleEntity = articleRepository.findById(articleEntity.getId()).get();
            assertEquals(expectedStock, articleEntity.getStock());
        }
    }
}
