package icu.debris.hrms.datarest.employee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
}
