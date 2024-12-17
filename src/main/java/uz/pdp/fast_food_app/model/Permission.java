package uz.pdp.fast_food_app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
public class Permission extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Builder
    public Permission(String name) {
        this.name = name;
    }
}
