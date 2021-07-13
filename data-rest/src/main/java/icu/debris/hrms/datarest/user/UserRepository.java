package icu.debris.hrms.datarest.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Cacheable
    Optional<User> findByUname(String uname);
}
