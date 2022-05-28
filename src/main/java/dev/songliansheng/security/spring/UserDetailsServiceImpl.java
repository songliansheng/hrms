package dev.songliansheng.security.spring;

import dev.songliansheng.datarest.user.User;
import dev.songliansheng.datarest.user.UserRepo;
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
