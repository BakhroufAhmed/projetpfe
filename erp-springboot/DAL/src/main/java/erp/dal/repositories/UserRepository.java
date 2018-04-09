package erp.dal.repositories;

import java.util.List;


import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import erp.bll.entities.AppUser;

/*
import org.sid.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long>{
public AppUser findByUsername(String username);
}*/
@Repository
public interface UserRepository extends LdapRepository<AppUser> {
    AppUser findByUsername(String username);
    AppUser findByUsernameAndPassword(String username, String password);
    List<AppUser> findByUsernameLikeIgnoreCase(String username);
}