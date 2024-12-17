package uz.pdp.fast_food_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.fast_food_app.dto.AddressDTO;
import uz.pdp.fast_food_app.model.Address;
import uz.pdp.fast_food_app.model.User;
import uz.pdp.fast_food_app.repository.AddressRepo;
import uz.pdp.fast_food_app.repository.UserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final AddressRepo addressRepo;
    private final UserService userService;
    private final UserRepo userRepo;

    public Address create(AddressDTO addressDTO) {
        Address address = Address.builder()
                .street(addressDTO.street())
                .latitude(addressDTO.latitude())
                .langitude(addressDTO.langitude())
                .build();
        Address save = addressRepo.save(address);
        User currentUser = userService.getCurrentUser();
        currentUser.setAddress(List.of(save));
        userRepo.save(currentUser);
        return save;
    }

    public Address getUserAddress() {
        User user = userService.getCurrentUser();
        List<Address> addressList = user.getAddress();
        Address address = addressList.get(addressList.size()-1);
        return address;
    }

    public Address update(String id, AddressDTO addressDTO) {
        Address address = addressRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Address not found"));
        address.setStreet(addressDTO.street());
        address.setLatitude(addressDTO.latitude());
        address.setLangitude(addressDTO.langitude());
        return addressRepo.save(address);
    }
}
