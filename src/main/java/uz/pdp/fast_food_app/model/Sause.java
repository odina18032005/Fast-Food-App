package uz.pdp.fast_food_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sause extends BaseEntity {
    private String name;
    private Long price;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files file;
    @ManyToOne
    @JoinColumn(name = "restaurants_id")
    private Restaurant restaurant;

}
