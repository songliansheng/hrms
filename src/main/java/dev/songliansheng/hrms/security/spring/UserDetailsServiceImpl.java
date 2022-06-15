package dev.songliansheng.hrms.security.spring;

import dev.songliansheng.hrms.datarest.user.User;
import dev.songliansheng.hrms.datarest.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userrepo;
    public User loadUserByUsername(String username){
      return userrepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(format("User: %s, not found", username)));

    }
}
