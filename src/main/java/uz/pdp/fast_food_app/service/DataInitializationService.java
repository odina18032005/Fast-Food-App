package uz.pdp.fast_food_app.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.fast_food_app.model.Permission;
import uz.pdp.fast_food_app.model.Role;
import uz.pdp.fast_food_app.repository.PermissionRepo;
import uz.pdp.fast_food_app.repository.RoleRepo;

import java.util.Arrays;

@Service
public class DataInitializationService {
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @PostConstruct
    public void init() {
        // permession for user
        Permission favoritePermission = permissionRepo.findByName("FAVORITE")
                .orElseGet(() -> permissionRepo.save(new Permission("FAVORITE")));

        Permission updateLocPermission = permissionRepo.findByName("UPDATE_LOCATION")
                .orElseGet(() -> permissionRepo.save(new Permission("UPDATE_LOCATION")));
        Permission getUserLocationPermission = permissionRepo.findByName("GET_USER_LOCATION")
                .orElseGet(() -> permissionRepo.save(new Permission("GET_USER_LOCATION")));

        Permission locationPermission = permissionRepo.findByName("LOCATION")
                .orElseGet(() -> permissionRepo.save(new Permission("LOCATION")));

        Permission getAllPermission = permissionRepo.findByName("GET_ALL")
                .orElseGet(() -> permissionRepo.save(new Permission("GET_ALL")));

        Permission getPermission = permissionRepo.findByName("GET")
                .orElseGet(() -> permissionRepo.save(new Permission("GET")));

        Permission buyPermission = permissionRepo.findByName("BUY")
                .orElseGet(() -> permissionRepo.save(new Permission("BUY")));

        Permission showCardPermission = permissionRepo.findByName("SHOW_CARD")
                .orElseGet(() -> permissionRepo.save(new Permission("SHOW_CARD")));

        Permission oredPermission = permissionRepo.findByName("ORDER")
                .orElseGet(() -> permissionRepo.save(new Permission("ORDER")));

        // permession for admin
        Permission createPermission = permissionRepo.findByName("CREATE")
                .orElseGet(() -> permissionRepo.save(new Permission("CREATE")));

        Permission updatePermission = permissionRepo.findByName("UPDATE")
                .orElseGet(() -> permissionRepo.save(new Permission("UPDATE")));

        Permission deletePermission = permissionRepo.findByName("DELETE")
                .orElseGet(() -> permissionRepo.save(new Permission("DELETE")));

        Role userRole = roleRepo.findByName("USER")
                .orElseGet(() -> roleRepo.save(new Role("USER",
                        Arrays.asList(favoritePermission,updateLocPermission,getUserLocationPermission,locationPermission,getAllPermission,getPermission,buyPermission,showCardPermission,oredPermission))));

        Role adminRole = roleRepo.findByName("ADMIN")
                .orElseGet(() -> roleRepo.save(new Role("ADMIN", Arrays.asList(createPermission,updatePermission,deletePermission))));

    }
}
