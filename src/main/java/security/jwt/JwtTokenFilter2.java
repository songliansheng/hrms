package security.jwt;

import icu.debris.hrms.security.user.User;
import icu.debris.hrms.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.List.of;
import static java.util.Optional.ofNullable;
import static org.springframework.util.ObjectUtils.isEmpty;

//@Component

public class JwtTokenFilter2 extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtTokenUtil;
    @Autowired
    UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // Get authorization header and validate
//        System.out.println("jwt filer begin");
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
//            System.out.println(userRepo.findByUsername("debris404").orElse(null).getUsername());
            System.out.println("filter执行开始");
            System.out.println("什么情况？？？");
            chain.doFilter(request, response);
           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth);
            System.out.println("filter执行完毕");
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
//        System.out.println("get jwt token");
//        System.out.println(jwtTokenUtil.getUserNameFromJwtToken(token));


        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }
//        Set<Role> roles = new HashSet<>();
//        Role role1 = new Role("ROLE_USER");
//        roles.add(role1);
//        User userDetails = new User("debris404", "男", "123456", roles);
        // Get user identity and set it on the spring security context
        User userDetails =  userRepo.findByUsername(jwtTokenUtil.getUserNameFromJwtToken(token))
                .orElse(null);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of())
        );

        authentication
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

}
