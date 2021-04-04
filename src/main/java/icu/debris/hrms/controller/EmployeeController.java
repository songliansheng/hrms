package icu.debris.hrms.controller;
import icu.debris.hrms.pojo.Employee;
import icu.debris.hrms.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository repository;

    @GetMapping(path = "")
    public @ResponseBody
    Iterable<Employee> getAllEmployees() {
        return this.repository.findAll();
    }

    @PostMapping(path = "")
    public void addEmployees(Employee employee) {
        repository.save(employee);
    }
    @DeleteMapping (path = "/{id}")
    public void deleteEmployees(@PathVariable Long id) {
        repository.deleteById(id);
    }
    @PutMapping(path = "/{id}")
    public void updateEmployee(@RequestBody Employee employee,@PathVariable Long id){
        repository.findById(id);
        repository.save(employee);
    }

}
