package security.spring;

import icu.debris.hrms.security.user.User;
import icu.debris.hrms.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userrepo;
    public User loadUserByUsername(String username){
      return userrepo.findByUsername(username).orElse(null);

    }
}
