package dev.songliansheng.controller;

import dev.songliansheng.datarest.dept.DeptRepo;
import dev.songliansheng.datarest.job.Job;
import dev.songliansheng.datarest.job.JobRepo;
import dev.songliansheng.datarest.dept.Dept;
import dev.songliansheng.datarest.empl.Empl;
import dev.songliansheng.datarest.empl.EmplRepo;
import dev.songliansheng.datarest.user.*;
import dev.songliansheng.datarest.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    UserView saveUser(@RequestBody User user) {
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
        ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
        return projectionFactory.createProjection(UserView.class, user);
    }
    @PostMapping("empls")
    public @ResponseBody Empl saveEmpl(@RequestBody Empl empl){
        if(empl.getJob().getJname().equals(""))
            empl.setJob(null);
        empRepo.save(empl);
        return empl;
    }


    @PutMapping("/users/{id}")
    public @ResponseBody
    UserView updateUser(@PathVariable Long id, @RequestBody User user) {
        String encodedPassword;
        if (user.getPassword().equals("")) {
            User storedUser = userRepo.findById(id).orElse(null);
            assert storedUser != null;
            encodedPassword = storedUser.getPassword();
        } else
            encodedPassword = pwdEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByRolename(user.getLabel()));
        user.setRoles(roles);
        userRepo.save(user);
        ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
        return projectionFactory.createProjection(UserView.class, user);
    }
    @PutMapping("empls/{id}")
    public @ResponseBody Empl updateEmpl(@PathVariable Long id , @RequestBody Empl empl){
        if(empl.getJob().getJname().equals(""))
            empl.setJob(null);
        empRepo.save(empl);
        return empl;
    }
    @PutMapping("jobs/{id}")
    public @ResponseBody Job updateJob(@PathVariable Long id , @RequestBody Job job){
        if(job.getDept().getDname().equals(""))
            job.setDept(null);
        jobRepo.save(job);
        return job;
    }


    @DeleteMapping("depts/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        Dept dept = deptRepo.findById(id).orElse(null);
        List<Job> jobs = jobRepo.findByDept(dept);
        for (Job job : jobs) {
            job.setDept(null);
        }
        jobRepo.saveAll(jobs);
        deptRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("hello");
    }
    @DeleteMapping("jobs/{id}")
    public void deleteJob(@PathVariable Long id) {
        Job job = jobRepo.findById(id).orElse(null);
        List<Empl> empls = empRepo.findByJob(job);
        for (Empl empl : empls) {
            empl.setJob(null);
        }
        empRepo.saveAll(empls);
        jobRepo.deleteById(id);
    }
}
