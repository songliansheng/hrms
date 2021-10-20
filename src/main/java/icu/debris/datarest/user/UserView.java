package icu.debris.datarest.user;

import java.util.Set;

public interface UserView {
    Set<RoleView> getRoles();
    String getUsername();
    String getPassword();
    Long getId();
}
