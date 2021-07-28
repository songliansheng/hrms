package data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Department {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
