package dev.songliansheng.hrms.datarest.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends CrudRepository<Role,Long>
{
    Role getById(Integer id);
      Role findByRolename(String role);
//    RoleView findAllById();
}
