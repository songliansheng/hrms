package icu.debris.datarest.job;

import icu.debris.datarest.dept.Dept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface JobRepo extends CrudRepository<Job, Long> {
       List<Job> findByDept(Dept dept);
}
