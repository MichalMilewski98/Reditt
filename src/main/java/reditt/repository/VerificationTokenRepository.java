package reditt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reditt.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
