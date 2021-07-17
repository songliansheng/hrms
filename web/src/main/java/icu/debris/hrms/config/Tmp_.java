package icu.debris.hrms.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 该类用来配置CORS，也可以在spring security 中配置一个 CorsFilter，两种方法都行

 */
//@Configuration
@Component
public class Tmp_ implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "DELETE","POST");
    }
   /* @PostConstruct
    public void test(){
        User user = userrepo.findById(1L).orElse(null);
        Role role = new Role("2","2");
        rolerepo.save(role);
        System.out.println(rolerepo.count());
        if(user !=null)


        System.out.println(user == null);
        System.out.println("helloworld");

    }*/
}








