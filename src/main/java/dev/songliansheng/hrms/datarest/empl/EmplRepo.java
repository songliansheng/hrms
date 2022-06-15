package dev.songliansheng.hrms.datarest.empl;

import dev.songliansheng.hrms.datarest.job.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmplRepo extends CrudRepository<Empl,Long> {

       List<Empl> findByJob(Job job) ;
}
