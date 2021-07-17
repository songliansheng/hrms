package icu.debris.hrms.security.user;

import icu.debris.hrms.security.user.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
//        extends CrudRepository<User, Long>
{
    @Cacheable
    Optional<User> findByUsername(String uname);
}
