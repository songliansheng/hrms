package icu.debris.hrms.security.user;

import icu.debris.hrms.security.user.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository
       extends CrudRepository<Role,Long>
{
}
