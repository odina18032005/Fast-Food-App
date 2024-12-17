package uz.pdp.fast_food_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Favorite extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private boolean isFavorite;
}
