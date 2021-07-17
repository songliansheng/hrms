package icu.debris.hrms.security.user;

import icu.debris.hrms.security.user.Role;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String username;
    public String sex;
    public String password;
    @Transient
    private boolean enabled = true;
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


//     implement UserDetails begin

    public Set<Role> getAuthorities() {
        return roles;
    }

    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

//     implement UserDetails end


    public User(String username, String sex, String password, Set<Role> roles) {
        this.username = username;
        this.sex = sex;
        this.password = password;
        this.roles = roles;
    }
}
