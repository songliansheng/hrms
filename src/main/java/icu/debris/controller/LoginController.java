package icu.debris.controller;

import icu.debris.datarest.employee.EmployeeRepository;
import icu.debris.datarest.user.User;
import icu.debris.datarest.user.UserRepository;
import icu.debris.payload.request.LoginRequest;
import icu.debris.payload.response.LogInResponse;
import icu.debris.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        User user = (User) authentication.getPrincipal();

        LogInResponse resBody = new LogInResponse(user.getUsername(),jwtUtils.generateJwtToken(user));
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtUtils.generateJwtToken(user))

                .body(resBody);
    }


}
