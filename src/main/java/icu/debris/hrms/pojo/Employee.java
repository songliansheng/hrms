package icu.debris.hrms.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
 public class Employee {
     private String ename;
     private  String sex;
     private  String education;
     private  String phone;
     private  String department;
     private  String job;
    private Long id;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
@Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
