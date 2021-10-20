package icu.debris.controller;

import icu.debris.datarest.department.Department;
import icu.debris.datarest.department.DepartmentRepository;
import icu.debris.datarest.employee.Employee;
import icu.debris.datarest.employee.EmployeeRepository;
import icu.debris.datarest.job.Job;
import icu.debris.datarest.job.JobRepository;
import icu.debris.datarest.user.User;
import icu.debris.datarest.user.UserRepository;
import icu.debris.datarest.user.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RepositoryRestController
public class CustomRepoController {
    @Autowired
    EmployeeRepository empRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    DepartmentRepository deptRepo;
    @Autowired
    JobRepository jobRepo;
    @Autowired
    PasswordEncoder pwdEncoder;

    @GetMapping("/employees")
    public @ResponseBody
    List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        empRepo.findAll().forEach(employees::add);
        return employees;
    }

    @GetMapping("/users")
    public @ResponseBody
    List<UserView> getUsers() {
        return new ArrayList<>(userRepo.findBy());
    }

    @GetMapping("/jobs")
    public @ResponseBody
    List<Job> getJobs() {
        List<Job> jobs = new ArrayList<>();
          jobRepo.findAll().forEach(jobs::add);
           return jobs;

    }

    @GetMapping("/depts")
    public @ResponseBody
    List<Department> getDepartments() {
        List<Department> depts = new ArrayList<>();
        deptRepo.findAll().forEach(depts::add);
        return depts;
    }

    @PostMapping("/users")
    public @ResponseBody
    User saveUser(@RequestBody User user) {
        String encodedPassword = pwdEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return user;
    }

    @PutMapping("/users/{id}")
    public @ResponseBody
    User updateUser(@PathVariable Long id, @RequestBody User user) {
        String encodedPassword;
        if (user.getPassword().equals("")) {
            User storedUser = userRepo.findById(id).orElse(null);
            encodedPassword = storedUser.getPassword();
        } else
            encodedPassword = pwdEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
        return user;
    }

    @DeleteMapping("depts/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        Department department = deptRepo.findById(id).orElse(null);
        List<Job> jobs = jobRepo.findByDepartment(department);
        Department nullDept = null;
        for (Job job : jobs) {
            job.setDepartment(nullDept);
        }
        jobRepo.saveAll(jobs);
        deptRepo.deleteById(id);
    }

    @DeleteMapping("jobs/{id}")
    public void deleteJob(@PathVariable Long id) {
        Job job = jobRepo.findById(id).orElse(null);
        List<Employee> employees = empRepo.findByJob(job);
        Job nullJob = null;
        for (Employee employee : employees) {
            employee.setJob(nullJob);
        }
        empRepo.saveAll(employees);
        jobRepo.deleteById(id);
    }
}
