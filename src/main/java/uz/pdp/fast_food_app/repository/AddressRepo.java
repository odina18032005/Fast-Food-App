package uz.pdp.fast_food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.fast_food_app.model.Address;

public interface AddressRepo extends JpaRepository<Address, String> {
}
