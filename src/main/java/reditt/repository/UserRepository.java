package reditt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reditt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
