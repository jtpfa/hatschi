create table pcmr_addresses
(
    id                 bigint       not null auto_increment,
    created            datetime(6),
    created_by         varchar(100),
    last_modified_by   varchar(100),
    updated            datetime(6),
    additional_address varchar(255),
    address            varchar(255) not null,
    city               varchar(100) not null,
    country            varchar(100) not null,
    first_name         varchar(50)  not null,
    last_name          varchar(50)  not null,
    zip                varchar(50)  not null,
    customer_id        bigint       not null,
    primary key (id)
) engine = InnoDB;
create table pcmr_order_items
(
    id               bigint  not null auto_increment,
    created          datetime(6),
    created_by       varchar(100),
    last_modified_by varchar(100),
    updated          datetime(6),
    quantity         integer not null check (quantity <= 99999999 AND quantity >= 0),
    article_id       bigint  not null,
    order_id         bigint  not null,
    primary key (id)
) engine = InnoDB;
create table pcmr_orders
(
    id                  bigint      not null auto_increment,
    created             datetime(6),
    created_by          varchar(100),
    last_modified_by    varchar(100),
    updated             datetime(6),
    order_date          datetime(6) not null,
    order_status        integer     not null,
    paid                bit         not null,
    payment_method      integer     not null,
    shipping_method     integer     not null,
    customer_id         bigint      not null,
    invoice_address_id  bigint      not null,
    shipping_address_id bigint      not null,
    primary key (id)
) engine = InnoDB;

alter table pcmr_order_items
    add constraint FK_order_items_articles foreign key (article_id) references pcmr_articles (id);
alter table pcmr_order_items
    add constraint FK_order_items_orders foreign key (order_id) references pcmr_orders (id);
alter table pcmr_orders
    add constraint FK_orders_customers foreign key (customer_id) references pcmr_customers (id);
alter table pcmr_addresses
    add constraint FK_addresses_customers foreign key (customer_id) references pcmr_customers (id);
alter table pcmr_orders
    add constraint FK_orders_addresses_invoice foreign key (invoice_address_id) references pcmr_addresses (id);
alter table pcmr_orders
    add constraint FK_orders_addresses_shipping foreign key (shipping_address_id) references pcmr_addresses (id);