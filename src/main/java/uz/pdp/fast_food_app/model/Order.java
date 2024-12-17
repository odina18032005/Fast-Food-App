package uz.pdp.fast_food_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;
    private int count;
    @ManyToOne
    @JoinColumn(name = "aPackage_id")
    private Package aPackage;
    @ManyToOne
    @JoinColumn(name = "sause_id")
    private Sause sause;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
