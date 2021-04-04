package icu.debris.hrms.service;

import icu.debris.hrms.pojo.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeJpa extends CrudRepository<Employee,Long> {
    List<Employee> findById(Long id)
}
