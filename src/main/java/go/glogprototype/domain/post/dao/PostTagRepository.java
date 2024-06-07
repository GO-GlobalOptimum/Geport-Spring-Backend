package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.post.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag,Long> {

    List<PostTag> findByPost(Post post);

}
