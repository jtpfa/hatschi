package de.pcmr.shop.builder;

import de.pcmr.shop.domain.ArticleEntity;
import de.pcmr.shop.domain.OrderItemEntity;

import java.util.Date;

/**
 * @author Fynn Lohse
 */

public final class OrderItemEntityBuilder {
    private Integer quantity;
    private ArticleEntity article;
    private Integer price;
    private Long id;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;

    private OrderItemEntityBuilder() {
    }

    public static OrderItemEntityBuilder anOrderItemEntity() {
        return new OrderItemEntityBuilder();
    }

    public OrderItemEntityBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderItemEntityBuilder withArticle(ArticleEntity article) {
        this.article = article;
        return this;
    }

    public OrderItemEntityBuilder withPrice(Integer price) {
        this.price = price;
        return this;
    }

    public OrderItemEntityBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public OrderItemEntityBuilder withCreated(Date created) {
        this.created = created;
        return this;
    }

    public OrderItemEntityBuilder withUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    public OrderItemEntityBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OrderItemEntityBuilder withLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public OrderItemEntity build() {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setQuantity(quantity);
        orderItemEntity.setArticle(article);
        orderItemEntity.setPrice(price);
        orderItemEntity.setId(id);
        orderItemEntity.setCreated(created);
        orderItemEntity.setUpdated(updated);
        orderItemEntity.setCreatedBy(createdBy);
        orderItemEntity.setLastModifiedBy(lastModifiedBy);
        return orderItemEntity;
    }
}
