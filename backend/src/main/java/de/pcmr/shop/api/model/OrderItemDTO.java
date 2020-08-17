package de.pcmr.shop.api.model;

import javax.validation.constraints.NotNull;

public class OrderItemDTO {
    @NotNull
    private long article;
    @NotNull
    private int quantity;

    public long getArticle() {
        return article;
    }

    public void setArticle(long article) {
        this.article = article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
