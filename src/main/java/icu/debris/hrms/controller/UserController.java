package icu.debris.hrms.controller;

import icu.debris.hrms.pojo.Employee;
import icu.debris.hrms.pojo.User;
import icu.debris.hrms.repository.EmployeeRepository;
import icu.debris.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping(path = "")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return this.repository.findAll();
    }

    @PostMapping(path = "")
    public void addUser(User user) {
        repository.save(user);
    }
    @DeleteMapping (path = "/{id}")
    public void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
    @PutMapping(path = "/{id}")
    public void updateUser(@RequestBody User user,@PathVariable Long id){
        repository.findById(id);
        repository.save(user);
    }

}