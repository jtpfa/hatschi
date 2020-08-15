package de.pcmr.shop.api.model;

public class OrderItemDTO {
    private long article;
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
