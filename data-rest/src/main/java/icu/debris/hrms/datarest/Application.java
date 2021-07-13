package icu.debris.hrms.datarest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;




@SpringBootApplication
@EnableJpaRepositories("icu.debris.hrms.datarest.employee")
@EntityScan("icu.debris.hrms.dataRest.employee")

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
