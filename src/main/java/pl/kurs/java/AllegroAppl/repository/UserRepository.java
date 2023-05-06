package pl.kurs.java.AllegroAppl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.java.AllegroAppl.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
