package dev.songliansheng.datarest;

import dev.songliansheng.datarest.job.Job;
import dev.songliansheng.datarest.dept.Dept;
import dev.songliansheng.datarest.empl.Empl;
import dev.songliansheng.datarest.user.Role;
import dev.songliansheng.datarest.user.User;
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
