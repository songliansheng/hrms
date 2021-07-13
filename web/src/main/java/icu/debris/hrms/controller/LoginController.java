package icu.debris.hrms.controller;

import icu.debris.hrms.datarest.employee.Employee;
import icu.debris.hrms.datarest.employee.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import icu.debris.hrms.request.LoginRequest;
import icu.debris.hrms.security.jwt.JwtUtils;
import  icu.debris.hrms.response.LogInResponse;

import java.io.Console;
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

    @PostMapping(value = "/login")
    /*
    ,produces = MediaType.APPLICATION_JSON_VALUE
     */
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LogInResponse resPayload;
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        HashMap<String,String> resBody = new HashMap<>();
        resBody.put("accessToken",jwtUtils.generateJwtToken(user));
        resBody.put("username",user.getUsername());
        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.add(HttpHeaders.AUTHORIZATION, "MyValue");

        return ResponseEntity.ok()
                //.header(HttpHeaders.AUTHORIZATION, jwtUtils.generateJwtToken(user))
                .body(resBody);

/*
        return new ResponseEntity<>(resBody,responseHeaders, HttpStatus.OK);

 */
    }

    @GetMapping("/employees")
    public @ResponseBody List<Employee> getEmployees(){
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;

    }

}
