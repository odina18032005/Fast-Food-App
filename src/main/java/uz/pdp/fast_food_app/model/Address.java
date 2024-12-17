package uz.pdp.fast_food_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address extends BaseEntity {
    private String street;
    private double latitude;
    private double langitude;

    @Builder
    public Address(String id, String createBy, LocalDateTime createdDate, LocalDateTime updatedDate, String updateBy, String street, double latitude, double langitude) {
        super(id, createBy, createdDate, updatedDate, updateBy);
        this.street = street;
        this.latitude = latitude;
        this.langitude = langitude;
    }
}
