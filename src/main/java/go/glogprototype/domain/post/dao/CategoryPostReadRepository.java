package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.CategoryPost;
import go.glogprototype.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryPostReadRepository extends JpaRepository <CategoryPost,Long> {

    List<CategoryPost> findByPost(Post post);

}
