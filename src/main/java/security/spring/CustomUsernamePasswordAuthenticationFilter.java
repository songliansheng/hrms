package security.spring;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

//@Component
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        if (request.getHeader("Content-Type").equals(MediaType.APPLICATION_JSON.toString())) {
//            String username = request.getParameter("username");
//            String password = request.getParameter("password");
            System.out.println("我要执行了");
            LoginRequest loginRequest = this.getLoginRequest(request);
            System.out.println("得到的用户名是" + loginRequest.getUsername());
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                    , loginRequest.getPassword());

//            setDetails(request, authRequest);
            Authentication auth = this.getAuthenticationManager().authenticate(authRequest);
            System.out.println(auth);
            System.out.println("我执行完毕了吗???");
            return auth;

//            return this.getAuthenticationManager().authenticate(authRequest);

        }
        Authentication auth = super.attemptAuthentication(request, response);
        System.out.println(auth);
        System.out.println("我执行完毕了");
        return auth;
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    private LoginRequest getLoginRequest(HttpServletRequest request) {
        BufferedReader reader = null;
        LoginRequest loginRequest = null;
        try {
            reader = request.getReader();
            Gson gson = new Gson();
            loginRequest = gson.fromJson(reader, LoginRequest.class);
        } catch (IOException ex) {
            System.out.println(ex);
//            log.error("CustomUsernamePasswordAuthenticationFilter#getLoginRequest", ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
//                log.error("CustomUsernamePasswordAuthenticationFilter#getLoginRequest", ex);
                System.out.println("CustomUsernamePasswordAuthenticationFilter#getLoginRequest");
            }
        }

        if (loginRequest == null) {
            loginRequest = new LoginRequest();
        }

        return loginRequest;
    }

    @Data
    private static class LoginRequest {

        String username;
        String password;

    }
}
