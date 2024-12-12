package lesson.uz.config;

import lesson.uz.entity.UserEntity;
import lesson.uz.enums.GeneralStatus;
import lesson.uz.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String id;
    private String name;
    private String surname;
    private String phone;
    private String password;
    private UserRole role;
    private GeneralStatus status;

    public CustomUserDetails(UserEntity user) {
        this.phone = user.getPhone();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.name()));
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(GeneralStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public GeneralStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public UserRole getRole() {
        return role;
    }
}
