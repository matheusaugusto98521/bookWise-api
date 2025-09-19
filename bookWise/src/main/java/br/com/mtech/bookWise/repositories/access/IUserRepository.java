package br.com.mtech.bookWise.repositories.access;

import br.com.mtech.bookWise.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    UserDetails findByUsername(String username);
    User findByEmailUser(String emailUser);
}
