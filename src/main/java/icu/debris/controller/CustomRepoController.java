package icu.debris.controller;

import icu.debris.datarest.dept.Dept;
import icu.debris.datarest.dept.DeptRepo;
import icu.debris.datarest.empl.Empl;
import icu.debris.datarest.empl.EmplRepo;
import icu.debris.datarest.job.Job;
import icu.debris.datarest.job.JobRepo;
import icu.debris.datarest.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RepositoryRestController
public class CustomRepoController {
    @Autowired
    EmplRepo empRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    DeptRepo deptRepo;
    @Autowired
    JobRepo jobRepo;
    @Autowired
    PasswordEncoder pwdEncoder;

    @GetMapping("/empls")
    public @ResponseBody
    List<Empl> getEmployees() {
        List<Empl> empls = new ArrayList<>();
        empRepo.findAll().forEach(empls::add);
        return empls;
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
    List<Dept> getDepartments() {
        List<Dept> depts = new ArrayList<>();
        deptRepo.findAll().forEach(depts::add);
        return depts;
    }

    @PostMapping("/users")
    public @ResponseBody
    User saveUser(@RequestBody User user) {
        String encodedPassword;
        String rolename = "";
        encodedPassword = pwdEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        if (user.getLabel().equals("管理员"))
            rolename = "ROLE_ADMIN";
        if (user.getLabel().equals("普通用户"))
            rolename = "ROLE_USER";
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByRolename(rolename));
        user.setRoles(roles);

        userRepo.save(user);
        return user;
    }

    @PutMapping("/users/{id}")
    public @ResponseBody
    User updateUser(@PathVariable Long id, @RequestBody User user) {
        String encodedPassword ;
        if (user.getPassword().equals("")) {
            User storedUser = userRepo.findById(id).orElse(null);
            encodedPassword = storedUser.getPassword();
        } else
            encodedPassword = pwdEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByRolename(user.getLabel()));
        user.setRoles(roles);

        userRepo.save(user);
        return user;
    }

    @DeleteMapping("depts/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        Dept dept = deptRepo.findById(id).orElse(null);
        List<Job> jobs = jobRepo.findByDept(dept);
        //   Department nullDept = null;
        for (Job job : jobs) {
            job.setDepartment(null);
        }
        jobRepo.saveAll(jobs);
        deptRepo.deleteById(id);
    }

    @DeleteMapping("jobs/{id}")
    public void deleteJob(@PathVariable Long id) {
        Job job = jobRepo.findById(id).orElse(null);
        List<Empl> empls = empRepo.findByJob(job);
        //     Job nullJob = null;
        for (Empl empl : empls) {
            empl.setJob(null);
        }
        empRepo.saveAll(empls);
        jobRepo.deleteById(id);
    }
}
