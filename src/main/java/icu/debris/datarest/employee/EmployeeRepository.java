package icu.debris.datarest.employee;

import icu.debris.datarest.job.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@CrossOrigin(methods = { RequestMethod.GET,RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE })
@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {

       List<Employee> findByJob(Job job) ;
}
