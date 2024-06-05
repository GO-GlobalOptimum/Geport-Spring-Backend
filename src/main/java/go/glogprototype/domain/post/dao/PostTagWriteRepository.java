package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagWriteRepository extends JpaRepository<PostTag,Long> {

}
