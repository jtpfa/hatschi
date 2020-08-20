package de.pcmr.shop.domain;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

import static de.pcmr.shop.domain.AbstractEntity.TABLE_PREFIX;

@Entity
@Table(name = TABLE_PREFIX + "order_items")
public class OrderItemEntity extends AbstractEntity {

    @Column(nullable = false)
    @Range(min = 0, max = 99999999, message = "Anzahl muss zwischen {min} und {max} Cent liegen")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ArticleEntity article;

    @Column(nullable = false)
    private Integer price;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ArticleEntity getArticle() {
        return article;
    }

    public void setArticle(ArticleEntity article) {
        this.article = article;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
