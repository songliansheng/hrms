package icu.debris.datarest;

import icu.debris.datarest.dept.Dept;
import icu.debris.datarest.empl.Empl;
import icu.debris.datarest.job.Job;
import icu.debris.datarest.user.Role;
import icu.debris.datarest.user.User;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
@Component
public class DataRestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Empl.class, Job.class, User.class, Dept.class, Role.class);
    }
}
