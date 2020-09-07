package de.pcmr.shop.db;

import de.pcmr.shop.builder.*;
import de.pcmr.shop.domain.*;
import de.pcmr.shop.exception.keycloak.KeycloakEndpointNotFoundException;
import de.pcmr.shop.exception.keycloak.KeycloakUnknownErrorException;
import de.pcmr.shop.exception.keycloak.KeycloakUserAlreadyExistsException;
import de.pcmr.shop.exception.keycloak.KeycloakUserIsNotAuthorizedException;
import de.pcmr.shop.repository.*;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.*;

@Service
public class DBPreparer {
    @Autowired private AddressRepository addressRepository;
    @Autowired private ArticleRepository articleRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private OrderRepository orderRepository;

    @Value("${PCMR_AUTH_SERVER_URL}") private String keycloakUrl;
    @Value("${PCMR_KEYCLOAK_REALM}") private String keycloakRealm;
    @Value("${PCMR_RESOURCE}") private String keycloakClient;
    @Value("${PCMR_KEYCLOAK_REGISTRATION_USER}") private String keycloakRegistrationUser;
    @Value("${PCMR_KEYCLOAK_REGISTRATION_PASSWORD}") private String keycloakRegistrationPassword;

    private Keycloak keycloak;

    private Map<String, CustomerEntity> customers = new HashMap<>();
    private Map<String, AddressEntity> addresses = new HashMap<>();
    private Map<String, ArticleEntity> articles = new HashMap<>();
    private Map<String, OrderEntity> orders = new HashMap<>();

    @PostConstruct
    public void initKeycloak() {
        keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(keycloakRealm)
                .clientId(keycloakClient)
                .username(keycloakRegistrationUser)
                .password(keycloakRegistrationPassword)
                .build();
    }

    public void prepareTestDatabase() throws KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUserIsNotAuthorizedException {
        cleanupDatabase();
        cleanupKeycloak();
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

    public void cleanupKeycloak() {
        UsersResource usersResource = keycloak.realm(keycloakRealm).users();

        for (UserRepresentation user : usersResource.list()) {
            if (!user.getUsername().equals(keycloakRegistrationUser)) {
                usersResource.delete(user.getId());
            }
        }
    }

    private void createCustomerEntities() throws KeycloakEndpointNotFoundException, KeycloakUnknownErrorException, KeycloakUserAlreadyExistsException, KeycloakUserIsNotAuthorizedException {
        CustomerEntity customerA = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("a@customer")
                .withFirstName("Peter")
                .withLastName("Pan")
                .withPassword("Password1!")
                .build();

        CustomerEntity customerB = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("b@customer")
                .withFirstName("Petra")
                .withLastName("Pan")
                .withPassword("Password1!")
                .build();

        CustomerEntity customerC = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("c@customer")
                .withFirstName("Lara")
                .withLastName("Müller")
                .withPassword("Password1!")
                .build();

        CustomerEntity customerD = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("d@customer")
                .withFirstName("Michael")
                .withLastName("Wandler")
                .withPassword("Password1!")
                .build();

        CustomerEntity employeeA = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("a@employee")
                .withFirstName("John")
                .withLastName("Smith")
                .withPassword("Password1!")
                .build();

        CustomerEntity employeeB = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("b@employee")
                .withFirstName("Jane")
                .withLastName("Scott")
                .withPassword("Password1!")
                .build();

        CustomerEntity employeeC = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("c@employee")
                .withFirstName("Mehmet")
                .withLastName("Uzun")
                .withPassword("Password1!")
                .build();

        CustomerEntity adminA = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("a@admin")
                .withFirstName("Lukas")
                .withLastName("Meier")
                .withPassword("Password1!")
                .build();

        CustomerEntity adminB = CustomerEntityBuilder.aCustomerEntity()
                .withEmail("b@admin")
                .withFirstName("Sabine")
                .withLastName("Berger")
                .withPassword("Password1!")
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

        createKeycloakUser(customerA, CustomerRoleEnum.CUSTOMER);
        createKeycloakUser(customerB, CustomerRoleEnum.CUSTOMER);
        createKeycloakUser(customerC, CustomerRoleEnum.CUSTOMER);
        createKeycloakUser(customerD, CustomerRoleEnum.CUSTOMER);
        createKeycloakUser(employeeA, CustomerRoleEnum.EMPLOYEE);
        createKeycloakUser(employeeB, CustomerRoleEnum.EMPLOYEE);
        createKeycloakUser(employeeC, CustomerRoleEnum.EMPLOYEE);
        createKeycloakUser(adminA, CustomerRoleEnum.ADMIN);
        createKeycloakUser(adminB, CustomerRoleEnum.ADMIN);

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

    public void createKeycloakUser(CustomerEntity customerEntity, CustomerRoleEnum customerRole) throws KeycloakUserIsNotAuthorizedException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUnknownErrorException {
        try {
            UserRepresentation user = createKeycloakUserFromCustomer(customerEntity);

            RealmResource realmResource = keycloak.realm(keycloakRealm);
            UsersResource usersResource = realmResource.users();

            Response response = usersResource.create(user);
            String userId = CreatedResponseUtil.getCreatedId(response);

            CredentialRepresentation passwordCredentials = createCredentialsFromCustomer(customerEntity);

            UserResource userResource = usersResource.get(userId);
            userResource.resetPassword(passwordCredentials);

            updateKeycloakUserRoles(customerRole, userResource, realmResource);

        } catch (WebApplicationException ex) {
            handleWebApplicationException(ex);
        }
    }

    private void handleWebApplicationException(WebApplicationException ex) throws KeycloakUserIsNotAuthorizedException, KeycloakUserAlreadyExistsException, KeycloakEndpointNotFoundException, KeycloakUnknownErrorException {
        switch (ex.getResponse().getStatus()) {
            case 401:
                throw new KeycloakUserIsNotAuthorizedException(ex);
            case 404:
                throw new KeycloakEndpointNotFoundException(ex);
            case 409:
                throw new KeycloakUserAlreadyExistsException(ex);
            case 400:
                throw new KeycloakUnknownErrorException();
            default:
                throw ex;
        }
    }

    private UserRepresentation createKeycloakUserFromCustomer(CustomerEntity customerEntity) {
        UserRepresentation user = new UserRepresentation();
        user.setEmail(customerEntity.getEmail());
        user.setFirstName(customerEntity.getFirstName());
        user.setLastName(customerEntity.getLastName());
        user.setUsername(customerEntity.getEmail());
        user.setAttributes(Collections.singletonMap("origin", Collections.singletonList("pcmr_test")));
        user.setEnabled(true);

        return user;
    }

    private CredentialRepresentation createCredentialsFromCustomer(CustomerEntity customerEntity) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(customerEntity.getPassword());

        return passwordCredentials;
    }

    private void updateKeycloakUserRoles(CustomerRoleEnum targetCustomerPrivileges, UserResource userResource, RealmResource realmResource) {
        List<RoleRepresentation> roleRepresentationList = realmResource.roles().list();
        List<RoleRepresentation> rolesToAdd = new ArrayList<>();
        List<RoleRepresentation> rolesToRemove = new ArrayList<>();

        for (RoleRepresentation roleRepresentation : roleRepresentationList) {
            determineIfRoleToAddOrToRemove(targetCustomerPrivileges, rolesToAdd, rolesToRemove, roleRepresentation);
        }

        removeUserSessions(userResource, realmResource);
        userResource.roles().realmLevel().add(rolesToAdd);
        userResource.roles().realmLevel().remove(rolesToRemove);
    }

    private void removeUserSessions(UserResource userResource, RealmResource realmResource) {
        List<UserSessionRepresentation> userSessionRepresentations = userResource.getUserSessions();

        for (UserSessionRepresentation userSessionRepresentation : userSessionRepresentations) {
            realmResource.deleteSession(userSessionRepresentation.getId());
        }
    }

    private void determineIfRoleToAddOrToRemove(CustomerRoleEnum customerRoleEnum, List<RoleRepresentation> rolesToAdd, List<RoleRepresentation> rolesToRemove, RoleRepresentation roleRepresentation) {
        switch (customerRoleEnum) {
            case EMPLOYEE:
                if (roleRepresentation.getName().equals(CustomerRoleEnum.EMPLOYEE.toString())) {
                    rolesToAdd.add(roleRepresentation);
                } else if (roleRepresentation.getName().equals(CustomerRoleEnum.ADMIN.toString())) {
                    rolesToRemove.add(roleRepresentation);
                } break;
            case ADMIN:
                if (needsRoleToBeAddedForAdmin(roleRepresentation)) {
                    rolesToAdd.add(roleRepresentation);
                } break;
            case CUSTOMER:
                if (needsRoleToBeRemoveForCustomer(roleRepresentation)) {
                    rolesToRemove.add(roleRepresentation);
                } break;
            default:
                throw new IllegalStateException();
        }
    }

    private boolean needsRoleToBeRemoveForCustomer(RoleRepresentation roleRepresentation) {
        return roleRepresentation.getName().equals(CustomerRoleEnum.EMPLOYEE.toString())
                || roleRepresentation.getName().equals(CustomerRoleEnum.ADMIN.toString());
    }

    private boolean needsRoleToBeAddedForAdmin(RoleRepresentation roleRepresentation) {
        return roleRepresentation.getName().equals(CustomerRoleEnum.ADMIN.toString())
                || roleRepresentation.getName().equals(CustomerRoleEnum.EMPLOYEE.toString());
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
