package de.pcmr.shop.api.model;

import javax.validation.constraints.NotNull;

public class ArticleCreationDTO {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String details;
    @NotNull
    private int price;
    @NotNull
    private int stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
