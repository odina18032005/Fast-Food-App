package uz.pdp.fast_food_app.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private String id;
    private String email;
    private String password;
    private Set<GrantedAuthority> grantedAuthorities;

    public CustomUserDetails(String id, String email, String password, Set<GrantedAuthority> grantedAuthorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
