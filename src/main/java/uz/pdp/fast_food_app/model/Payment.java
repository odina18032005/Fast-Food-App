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
public class Payment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private double totals;
    private String cardNumber;
    private String status;
}
