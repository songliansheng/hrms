package dev.songliansheng.hrms.datarest.dept;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepo extends CrudRepository<Dept, Long> {
}
