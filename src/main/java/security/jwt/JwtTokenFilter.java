package security.jwt;

import icu.debris.hrms.security.user.UserRepository;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtTokenUtil;
    @Autowired
    UserRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // Get authorization header and validate
//        System.out.println("jwt filer begin");
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
//            System.out.println(userRepo.findByUsername("debris404").orElse(null).getUsername());

            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
//        System.out.println("get jwt token");
//        System.out.println(jwtTokenUtil.getUserNameFromJwtToken(token));
//        jwtTokenUtil.validate(token);
        try {
            jwtTokenUtil.validate(token);
            System.out.println("不是jwt验证？");

        } catch (SecurityException ex) {
//            response.setStatus(401);
            logger.error("Invalid JWT signature - {}");
            logger.info(ex.getMessage());
            chain.doFilter(request, response);
            return;
        }
        catch (SignatureException ex) {
//            response.setStatus(405);
            logger.error("该jwt无效");
            chain.doFilter(request, response);
            logger.info("所有filter执行完毕");
            return;
        }
//        User userDetails = userRepo.findByUsername(jwtTokenUtil.getUserNameFromJwtToken(token))
//                .orElse(null);
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                userDetails, null,
//                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of())
//        );
//
//        authentication
//                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        if (!jwtTokenUtil.validate(token)) {
//            chain.doFilter(request, response);
//            return;
//        }


//        Set<Role> roles = new HashSet<>();
//        Role role1 = new Role("ROLE_USER");
//        roles.add(role1);
//        User userDetails = new User("debris404", "男", "123456", roles);
        // Get user identity and set it on the spring security context
//        User userDetails = userRepo.findByUsername(jwtTokenUtil.getUserNameFromJwtToken(token))
//                .orElse(null);
//
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                userDetails, null,
//                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of())
//        );
//
//        authentication
//                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
