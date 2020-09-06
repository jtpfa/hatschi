package de.pcmr.shop.db;

import de.pcmr.shop.builder.*;
import de.pcmr.shop.domain.*;
import de.pcmr.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DBPreparer {
    @Autowired private AddressRepository addressRepository;
    @Autowired private ArticleRepository articleRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private OrderRepository orderRepository;

    private Map<String, CustomerEntity> customers = new HashMap<>();
    private Map<String, AddressEntity> addresses = new HashMap<>();
    private Map<String, ArticleEntity> articles = new HashMap<>();
    private Map<String, OrderEntity> orders = new HashMap<>();

    @Transactional
    public void prepareTestDatabase() {
        cleanupDatabase();
        createCustomerEntities();
        createArticleEntities();
        createCustomerAddresses();
        createOrderEntities();
    }

    public void cleanupDatabase() {
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();
        addressRepository.deleteAll();
        articleRepository.deleteAll();
        customerRepository.deleteAll();
    }

    private void createCustomerEntities() {
        CustomerEntity customerA = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("a@customer")
                .withFirstName("Peter")
                .withLastName("Pan")
                .build();

        CustomerEntity customerB = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("b@customer")
                .withFirstName("Petra")
                .withLastName("Pan")
                .build();

        CustomerEntity customerC = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("c@customer")
                .withFirstName("Lara")
                .withLastName("Müller")
                .build();

        CustomerEntity customerD = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("d@customer")
                .withFirstName("Michael")
                .withLastName("Wandler")
                .build();

        CustomerEntity employeeA = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("a@employee")
                .withFirstName("John")
                .withLastName("Smith")
                .build();

        CustomerEntity employeeB = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("b@employee")
                .withFirstName("Jane")
                .withLastName("Scott")
                .build();

        CustomerEntity employeeC = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("c@employee")
                .withFirstName("Mehmet")
                .withLastName("Uzun")
                .build();

        CustomerEntity adminA = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("a@admin")
                .withFirstName("Lukas")
                .withLastName("Meier")
                .build();

        CustomerEntity adminB = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("b@admin")
                .withFirstName("Sabine")
                .withLastName("Berger")
                .build();

        customerA = customerRepository.save(customerA);
        customerB = customerRepository.save(customerB);
        customerC = customerRepository.save(customerC);
        customerD = customerRepository.save(customerD);
        employeeA = customerRepository.save(employeeA);
        employeeB = customerRepository.save(employeeB);
        employeeC = customerRepository.save(employeeC);
        adminA = customerRepository.save(adminA);
        adminB = customerRepository.save(adminB);

        customers.put(customerA.getEmail(), customerA);
        customers.put(customerB.getEmail(), customerB);
        customers.put(customerC.getEmail(), customerC);
        customers.put(customerD.getEmail(), customerD);
        customers.put(employeeA.getEmail(), employeeA);
        customers.put(employeeB.getEmail(), employeeB);
        customers.put(employeeC.getEmail(), employeeC);
        customers.put(adminA.getEmail(), adminA);
        customers.put(adminB.getEmail(), adminB);
    }

    private void createArticleEntities() {
        ArticleEntity computer = ArticleEntityBuilder.anArticleEntity()
                .withName("computer")
                .withDescription("PC Description")
                .withDetails("PC Details")
                .withPrice(59999)
                .withStock(10)
                .build();
        ArticleEntity mouse = ArticleEntityBuilder.anArticleEntity()
                .withName("mouse")
                .withDescription("Mouse Description")
                .withDetails("Mouse Details")
                .withPrice(4999)
                .withStock(20)
                .build();
        ArticleEntity graphicscard = ArticleEntityBuilder.anArticleEntity()
                .withName("graphicscard")
                .withDescription("GPU Description")
                .withDetails("GPU Details")
                .withPrice(39999)
                .withStock(3)
                .build();
        ArticleEntity processor = ArticleEntityBuilder.anArticleEntity()
                .withName("processor")
                .withDescription("cpu Description")
                .withDetails("cpu Details")
                .withPrice(29999)
                .withStock(5)
                .build();
        ArticleEntity powersupply = ArticleEntityBuilder.anArticleEntity()
                .withName("powersupply")
                .withDescription("psu Description")
                .withDetails("psu Details")
                .withPrice(4999)
                .withStock(0)
                .build();
        ArticleEntity keyboard = ArticleEntityBuilder.anArticleEntity()
                .withName("keyboard")
                .withDescription("keyboard Description")
                .withDetails("keyboard Details")
                .withPrice(2999)
                .withStock(1)
                .build();
        ArticleEntity freebie = ArticleEntityBuilder.anArticleEntity()
                .withName("freebie")
                .withDescription("freebie Description")
                .withDetails("freebie Details")
                .withPrice(0)
                .withStock(30)
                .build();

        computer = articleRepository.save(computer);
        mouse = articleRepository.save(mouse);
        graphicscard = articleRepository.save(graphicscard);
        processor = articleRepository.save(processor);
        powersupply = articleRepository.save(powersupply);
        keyboard = articleRepository.save(keyboard);
        freebie = articleRepository.save(freebie);

        articles.put(computer.getName(), computer);
        articles.put(mouse.getName(), mouse);
        articles.put(graphicscard.getName(), graphicscard);
        articles.put(processor.getName(), processor);
        articles.put(powersupply.getName(), powersupply);
        articles.put(keyboard.getName(), keyboard);
        articles.put(freebie.getName(), freebie);
    }

    private void createCustomerAddresses() {
        AddressEntity addressCustomerA1 = AddressEntityBuilder.anAddressEntity()
                .withAddress("Testweg 1")
                .withAdditionalAddress("CA1")
                .withActive(true)
                .withFirstName("Peter")
                .withLastName("Pan")
                .withZip("98765")
                .withCity("München")
                .withCountry("Germany")
                .withCustomer(customers.get("a@customer"))
                .build();

        AddressEntity addressCustomerA2 = AddressEntityBuilder.anAddressEntity()
                .withAddress("Hauptstraße 3")
                .withAdditionalAddress("CA2")
                .withActive(false)
                .withFirstName("Peter")
                .withLastName("Pan")
                .withZip("54321")
                .withCity("Paderborn")
                .withCountry("Germany")
                .withCustomer(customers.get("a@customer"))
                .build();

        AddressEntity addressCustomerA3 = AddressEntityBuilder.anAddressEntity()
                .withAddress("Lange Straße 5")
                .withAdditionalAddress("CA3")
                .withActive(true)
                .withFirstName("Peter")
                .withLastName("Pan")
                .withZip("12345")
                .withCity("Bielefeld")
                .withCountry("Germany")
                .withCustomer(customers.get("a@customer"))
                .build();

        AddressEntity addressCustomerB1 = AddressEntityBuilder.anAddressEntity()
                .withAddress("Hauptstraße 3")
                .withAdditionalAddress("CB1")
                .withActive(false)
                .withFirstName("Petra")
                .withLastName("Pan")
                .withZip("54321")
                .withCity("Paderborn")
                .withCountry("Germany")
                .withCustomer(customers.get("b@customer"))
                .build();

        AddressEntity addressCustomerB2 = AddressEntityBuilder.anAddressEntity()
                .withAddress("Lange Straße 5")
                .withAdditionalAddress("CB2")
                .withActive(true)
                .withFirstName("Petra")
                .withLastName("Pan")
                .withZip("12345")
                .withCity("Bielefeld")
                .withCountry("Germany")
                .withCustomer(customers.get("b@customer"))
                .build();

        AddressEntity addressCustomerC1 = AddressEntityBuilder.anAddressEntity()
                .withAddress("Lange Straße 5")
                .withAdditionalAddress("CC1")
                .withActive(true)
                .withFirstName("Lara")
                .withLastName("Müller")
                .withZip("12345")
                .withCity("Köln")
                .withCountry("Germany")
                .withCustomer(customers.get("c@customer"))
                .build();

        addressCustomerA1 = addressRepository.save(addressCustomerA1);
        addressCustomerA2 = addressRepository.save(addressCustomerA2);
        addressCustomerA3 = addressRepository.save(addressCustomerA3);
        addressCustomerB1 = addressRepository.save(addressCustomerB1);
        addressCustomerB2 = addressRepository.save(addressCustomerB2);
        addressCustomerC1 = addressRepository.save(addressCustomerC1);

        addresses.put(addressCustomerA1.getAdditionalAddress(), addressCustomerA1);
        addresses.put(addressCustomerA2.getAdditionalAddress(), addressCustomerA2);
        addresses.put(addressCustomerA3.getAdditionalAddress(), addressCustomerA3);
        addresses.put(addressCustomerB1.getAdditionalAddress(), addressCustomerB1);
        addresses.put(addressCustomerB2.getAdditionalAddress(), addressCustomerB2);
        addresses.put(addressCustomerC1.getAdditionalAddress(), addressCustomerC1);
    }

    void createOrderEntities() {
        OrderItemEntity orderItemEntityComputer = OrderItemEntityBuilder.anOrderItemEntity()
                .withArticle(articles.get("computer"))
                .withQuantity(1)
                .withPrice(59999)
                .build();

        OrderItemEntity orderItemEntityMouse = OrderItemEntityBuilder.anOrderItemEntity()
                .withArticle(articles.get("mouse"))
                .withQuantity(1)
                .withPrice(4999)
                .build();

        OrderItemEntity orderItemEntityProcessor = OrderItemEntityBuilder.anOrderItemEntity()
                .withArticle(articles.get("processor"))
                .withQuantity(2)
                .withPrice(59998)
                .build();

        OrderItemEntity orderItemEntityProcessor2 = OrderItemEntityBuilder.anOrderItemEntity()
                .withArticle(articles.get("processor"))
                .withQuantity(1)
                .withPrice(27999)
                .build();

        OrderItemEntity orderItemEntityPowerSupply = OrderItemEntityBuilder.anOrderItemEntity()
                .withArticle(articles.get("powersupply"))
                .withQuantity(1)
                .withPrice(4999)
                .build();

        OrderItemEntity orderItemEntityKeyboard = OrderItemEntityBuilder.anOrderItemEntity()
                .withArticle(articles.get("keyboard"))
                .withQuantity(1)
                .withPrice(2999)
                .build();

        OrderItemEntity orderItemEntityFreebie = OrderItemEntityBuilder.anOrderItemEntity()
                .withArticle(articles.get("freebie"))
                .withQuantity(1)
                .withPrice(0)
                .build();

        OrderEntity orderEntityCA1 = OrderEntityBuilder.anOrderEntity()
                .withOrderItems(List.of(orderItemEntityComputer, orderItemEntityMouse, orderItemEntityKeyboard))
                .withOrderStatus(OrderStatusEnum.OPEN)
                .withPaid(false)
                .withCustomer(customers.get("a@customer"))
                .withPaymentMethod(PaymentMethodEnum.PAYPAL)
                .withOrderDate(new Date(1586011102000L))
                .withInvoiceAddress(addresses.get("CA1"))
                .withShippingAddress(addresses.get("CA3"))
                .withShippingMethod(ShippingMethodEnum.EXPRESS)
                .build();

        OrderEntity orderEntityCA2 = OrderEntityBuilder.anOrderEntity()
                .withOrderItems(List.of(orderItemEntityProcessor, orderItemEntityPowerSupply))
                .withOrderStatus(OrderStatusEnum.OPEN)
                .withPaid(false)
                .withCustomer(customers.get("a@customer"))
                .withPaymentMethod(PaymentMethodEnum.INVOICE)
                .withOrderDate(new Date(1586097502000L))
                .withInvoiceAddress(addresses.get("CA1"))
                .withShippingAddress(addresses.get("CA1"))
                .withShippingMethod(ShippingMethodEnum.DEFAULT)
                .build();

        OrderEntity orderEntityCB1 = OrderEntityBuilder.anOrderEntity()
                .withOrderItems(List.of(orderItemEntityFreebie, orderItemEntityProcessor2))
                .withOrderStatus(OrderStatusEnum.OPEN)
                .withPaid(false)
                .withCustomer(customers.get("b@customer"))
                .withPaymentMethod(PaymentMethodEnum.INVOICE)
                .withOrderDate(new Date(1583422702000L))
                .withInvoiceAddress(addresses.get("CB1"))
                .withShippingAddress(addresses.get("CB1"))
                .withShippingMethod(ShippingMethodEnum.DEFAULT)
                .build();

        orderEntityCA1 = orderRepository.save(orderEntityCA1);
        orderEntityCA2 = orderRepository.save(orderEntityCA2);
        orderEntityCB1 = orderRepository.save(orderEntityCB1);

        orders.put("CA1", orderEntityCA1);
        orders.put("CA2", orderEntityCA2);
        orders.put("CB1", orderEntityCB1);
    }

    public Map<String, CustomerEntity> getCustomers() {
        return customers;
    }

    public void setCustomers(Map<String, CustomerEntity> customers) {
        this.customers = customers;
    }

    public Map<String, AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(Map<String, AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public Map<String, ArticleEntity> getArticles() {
        return articles;
    }

    public void setArticles(Map<String, ArticleEntity> articles) {
        this.articles = articles;
    }

    public Map<String, OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, OrderEntity> orders) {
        this.orders = orders;
    }
}
