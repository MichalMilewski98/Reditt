package reditt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reditt.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
