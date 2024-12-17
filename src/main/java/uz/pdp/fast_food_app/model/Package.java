package uz.pdp.fast_food_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "packages")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Package extends BaseEntity {
    private String name;
    private Long price;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files file;
    @ManyToOne
    @JoinColumn(name = "restaurants_id")
    private Restaurant restaurant;

    @Builder
    public Package(String name, Long price, Files file, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.file = file;
        this.restaurant = restaurant;
    }
}
