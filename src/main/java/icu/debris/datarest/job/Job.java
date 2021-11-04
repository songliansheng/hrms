package icu.debris.datarest.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import icu.debris.datarest.dept.Dept;

import javax.persistence.*;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jname;
    private String notes;

    @ManyToOne
    @JoinTable(name = "job_department",
            joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "department_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Dept dept;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname;
    }

    public Dept getDepartment() {
        return dept;
    }

    public void setDepartment(Dept dept) {
        this.dept = dept;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

}
