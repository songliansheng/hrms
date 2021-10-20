package icu.debris.datarest.job;

import icu.debris.datarest.department.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobRepository extends CrudRepository<Job, Long> {
       List<Job> findByDepartment(Department department);
}
