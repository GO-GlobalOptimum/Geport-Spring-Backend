package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentWriteRepository extends JpaRepository<Comment, Long> {
}