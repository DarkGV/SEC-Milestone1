package pt.ulisboa.tecnico.milestone1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulisboa.tecnico.milestone1.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(int id);
    boolean existsByName(String name);
}
