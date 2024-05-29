package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
