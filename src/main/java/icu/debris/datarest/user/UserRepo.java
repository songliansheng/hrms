package icu.debris.datarest.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    @Cacheable
    Optional<User> findByUsername(String uname);

    Set<UserView> findBy();
}
