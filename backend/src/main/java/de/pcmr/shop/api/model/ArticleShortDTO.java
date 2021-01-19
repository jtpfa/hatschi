package de.pcmr.shop.api.model;

/**
 * Data transfer object to output short article information.
 *
 * @author Fynn Lohse
 */

public class ArticleShortDTO {
    private long id;
    private String name;
    private String description;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
