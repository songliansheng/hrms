package icu.debris.datarest.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long>
{
    RoleView getById(Integer id);
    RoleView findByRole(String role);
//    RoleView findAllById();
}
