package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.CategoryPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryPostWriteRepository extends JpaRepository<CategoryPost,Long> {
}
