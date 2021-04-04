package icu.debris.hrms.repository;
import icu.debris.hrms.pojo.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {

}
