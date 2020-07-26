package de.pcmr.shop.builder;

import de.pcmr.shop.domain.ArticleEntity;

import java.util.Date;

public final class ArticleEntityBuilder {
    private String name;
    private String description;
    private String details;
    private int price;
    private int stock;
    private long id;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;

    private ArticleEntityBuilder() {
    }

    public static ArticleEntityBuilder anArticleEntity() {
        return new ArticleEntityBuilder();
    }

    public ArticleEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ArticleEntityBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ArticleEntityBuilder withDetails(String details) {
        this.details = details;
        return this;
    }

    public ArticleEntityBuilder withPrice(int price) {
        this.price = price;
        return this;
    }

    public ArticleEntityBuilder withStock(int stock) {
        this.stock = stock;
        return this;
    }

    public ArticleEntityBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ArticleEntityBuilder withCreated(Date created) {
        this.created = created;
        return this;
    }

    public ArticleEntityBuilder withUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    public ArticleEntityBuilder withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public ArticleEntityBuilder withLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public ArticleEntity build() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setName(name);
        articleEntity.setDescription(description);
        articleEntity.setDetails(details);
        articleEntity.setPrice(price);
        articleEntity.setStock(stock);
        articleEntity.setId(id);
        articleEntity.setCreated(created);
        articleEntity.setUpdated(updated);
        articleEntity.setCreatedBy(createdBy);
        articleEntity.setLastModifiedBy(lastModifiedBy);
        return articleEntity;
    }
}
