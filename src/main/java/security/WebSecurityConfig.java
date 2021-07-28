package security;


import icu.debris.hrms.security.jwt.JwtTokenFilter;
import icu.debris.hrms.security.service.UserDetailsServiceImpl;
import icu.debris.hrms.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import security.spring.AuthEntryPointJwt;
import security.spring.CustomAuthenticationFailureHandler;
import security.spring.UserDetailsServiceImpl;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableWebMvc
//@EnableGlobalMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true,
//        prePostEnabled = true
//)
public class WebSecurityConfig

        extends WebSecurityConfigurerAdapter {
    @Autowired
    UserRepository userRepo;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
//    @Autowired
//    CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter;
    @Autowired
AuthEntryPointJwt unauthorizedHandler;
    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//       此处的UserDetailsService的实现，与 authentication.getPrincipal()的返回值对应
        auth.userDetailsService(
                username ->
                   userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException( format("User: %s, not found", username))))
//                userDetailsService()
        ;
//        auth.userDetailsService(username -> userRepo.findByUsername(username).orElse(null) );
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(encoder);
//        auth.inMemoryAuthentication()
//                .withUser("debris404")
//                .password(encoder.encode("123456"))
//        .roles("USER");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()

                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET,"/employees").hasRole("ADMIN")
//                .antMatchers("/employees").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
//                ;
                .and()
                .formLogin(formLogin ->
                                formLogin
                                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/postlogin")
//                                .loginPage("/login")
//                                .failureHandler(authenticationFailureHandler())
                );


        http.csrf().disable();
        http.headers().frameOptions().disable();
//        http.addFilterBefore(jwtTokenFilter, CustomUsernamePasswordAuthenticationFilter.class);
//        http.addFilterAfter(customUsernamePasswordAuthenticationFilter,JwtTokenFilter2.class);

//        http.addFilterAt(customUsernamePasswordAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);


    }


    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("debris404")
                        .password("{noop}123456")
                        .roles("ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }


    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public JwtTokenFilter2 authenticationJwtTokenFilter() {
//        return new JwtTokenFilter2();
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        //setAllowedHeaders() must be used to make this bean take effect
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}

