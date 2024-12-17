package uz.pdp.fast_food_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.fast_food_app.model.Permission;
import uz.pdp.fast_food_app.model.Role;
import uz.pdp.fast_food_app.model.User;
import uz.pdp.fast_food_app.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
            throw new BadCredentialsException("Username or password incorrect");
        }
        List<Role> roles = user.get().getRoles();
        if (roles.isEmpty()) {
            throw new BadCredentialsException("No roles assigned to user");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles)
            for (Permission permission : role.getPermissions()) {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + permission.getName());
                authorities.add(simpleGrantedAuthority);
            }
        UserDetails userDetails = getUserDetails(email, user, authorities);

        return userDetails;
    }

    private static UserDetails getUserDetails(String email, Optional<User> user, List<GrantedAuthority> authorities) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(email)
                .password(user.get().getPassword())
                .authorities(authorities)
                .build();
        return userDetails;
    }
}
