import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;

@SpringBootApplication(exclude = HypermediaAutoConfiguration.class)

//@Import(Config.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

/**
  该Bean配置CORS，也可以单独创建一个类
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("GET", "DELETE");
            }
        };
    }

**/





}
