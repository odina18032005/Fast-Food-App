package uz.pdp.fast_food_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User extends BaseEntity {
    private String name;
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "files_id")
    private Files file;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_adress",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> address;
    private String cardumber;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Builder
    public User(String name, String email, String password, Files file, List<Address> address, String cardumber, List<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.file = file;
        this.address = address;
        this.cardumber = cardumber;
        this.roles = roles;
    }
}
