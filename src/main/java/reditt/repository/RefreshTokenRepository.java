package reditt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reditt.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void findByToken(String token);

    void deleteByToken(String token);
}
