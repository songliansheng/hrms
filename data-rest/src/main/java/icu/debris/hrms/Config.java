package icu.debris.hrms;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@PropertySource("classpath:database.properties")
//@EnableJpaRepositories("icu.debris.hrms.dataRest")
//@EntityScan("icu.debris.hrms.dataRest")
public class Config {

}
