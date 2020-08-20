package de.pcmr.shop.api.model;

import javax.validation.constraints.NotNull;

public class OrderItemDTO {
    @NotNull
    private Long articleId;
    @NotNull
    private Integer quantity;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
