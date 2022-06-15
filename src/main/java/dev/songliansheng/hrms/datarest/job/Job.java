package dev.songliansheng.hrms.datarest.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.songliansheng.hrms.datarest.dept.Dept;

import javax.persistence.*;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jname;
    public String description;


    @ManyToOne(cascade = CascadeType.ALL)
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

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Job(String jname, String description, Dept dept) {
        this.jname = jname;
        this.description = description;
        this.dept = dept;
    }
    public Job() {
    }
}
