package controller;

import icu.debris.hrms.datarest.employee.Employee;
import icu.debris.hrms.datarest.employee.EmployeeRepository;
import icu.debris.hrms.request.LoginRequest;
import icu.debris.hrms.security.jwt.JwtUtils;
import icu.debris.hrms.security.user.User;
import icu.debris.hrms.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    UserRepository userrepo;

    @PostMapping(value = "/login")
//    @ExceptionHandler(CustomAuthenticationFailureHandler.class)
    /*
    ,produces = MediaType.APPLICATION_JSON_VALUE
     */
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
//       try {
           System.out.println("控制器方法 /postlogin 开始执行");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           System.out.println("请求的用户名: " + loginRequest.getUsername());
            User user = (User) authentication.getPrincipal();
            HashMap<String, String> resBody = new HashMap<>();
            resBody.put("accessToken", jwtUtils.generateJwtToken(user));
            resBody.put("username", user.getUsername());
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtUtils.generateJwtToken(user))
                    .body(resBody);
        }
//       catch (AuthenticationException ex){
//           System.out.println("哎，密码错误");
//           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//
//    }
//    }

    @GetMapping("/employees")
    public @ResponseBody List<Employee> getEmployees(){
//        System.out.println(userrepo.findByUsername("debris404").orElse(null));
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;

    }

}
