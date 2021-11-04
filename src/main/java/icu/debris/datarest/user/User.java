package icu.debris.datarest.user;

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
    public String password;
    public String description;
    public String label;
    @Transient
    private boolean enabled = true;
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    //这是条临时评论
    //注意此处的 cascade = CascadeType.ALL ，没有这一条会报错
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Role> roles
            = new HashSet<>();

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    //这是条临时评论

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

    //这是条临时评论
//   implement UserDetails end


    public User(String username, String password, Set<Role> roles, String label) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.label = label;
    }
}
