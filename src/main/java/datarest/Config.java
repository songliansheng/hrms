package datarest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:database.properties")
//@EnableJpaRepositories("icu.debris.hrms.dataRest")
//@EntityScan("icu.debris.hrms.dataRest")
public class Config {

}
