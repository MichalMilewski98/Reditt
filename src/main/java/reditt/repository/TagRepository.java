package reditt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reditt.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
