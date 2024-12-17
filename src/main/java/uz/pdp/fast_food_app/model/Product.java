package uz.pdp.fast_food_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    private String name;
    private Long price;
    private String description;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files file;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")  // Foreign key column
    private Restaurant restaurant;
}
