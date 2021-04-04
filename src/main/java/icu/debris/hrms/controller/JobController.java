package icu.debris.hrms.controller;
import icu.debris.hrms.pojo.Job;
import icu.debris.hrms.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/jobs")
public class JobController {
    @Autowired
    private JobRepository repository;

    @GetMapping(path = "")
    public @ResponseBody
    Iterable<Job> getAllJobs() {
        return this.repository.findAll();
    }

    @PostMapping(path = "")
    public void addJob(Job job) {
        repository.save(job);
    }
    @DeleteMapping (path = "/{id}")
    public void deleteJob(@PathVariable Long id) {
        repository.deleteById(id);
    }
    @PutMapping(path = "/{id}")
    public void updateJob(@RequestBody Job job,@PathVariable Long id){
        repository.findById(id);
        repository.save(job);
    }

}
