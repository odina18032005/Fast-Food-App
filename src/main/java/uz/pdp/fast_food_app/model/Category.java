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
public class Category extends BaseEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files files;
}