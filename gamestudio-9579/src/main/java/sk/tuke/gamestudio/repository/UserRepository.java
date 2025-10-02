package sk.tuke.gamestudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.tuke.gamestudio.entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByActivationCode(String code);
}


