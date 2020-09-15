package de.pcmr.shop.api.model;

import javax.validation.constraints.NotNull;

/**
 * @author Fynn Lohse
 */

public class ArticleDTO {
    @NotNull(message = "Keine Id angegeben")
    private long id;
    @NotNull(message = "Kein Name angegeben")
    private String name;
    @NotNull(message = "Keine Beschreibung angegeben")
    private String description;
    @NotNull(message = "Keine Details angegeben")
    private String details;
    @NotNull(message = "Kein Preis angegeben")
    private int price;
    @NotNull(message = "Kein Bestand angegeben")
    private int stock;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
