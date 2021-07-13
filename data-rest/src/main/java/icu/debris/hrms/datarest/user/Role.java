package icu.debris.hrms.datarest.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    String id;
    String roleName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
