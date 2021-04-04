package icu.debris.hrms.controller;
import icu.debris.hrms.pojo.Department;
import icu.debris.hrms.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository repository;

    @GetMapping(path = "")
    public @ResponseBody
    Iterable<Department> getAllEmployees() {
        return this.repository.findAll();
    }

    @PostMapping(path = "")
    public void addEmployees(Department employee) {
        repository.save(employee);
    }
    @DeleteMapping (path = "/{id}")
    public void deleteEmployees(@PathVariable Long id) {
        repository.deleteById(id);
    }
    @PutMapping(path = "/{id}")
    public void updateEmployee(@RequestBody Department employee,@PathVariable Long id){
        repository.findById(id);
        repository.save(employee);
    }

}
