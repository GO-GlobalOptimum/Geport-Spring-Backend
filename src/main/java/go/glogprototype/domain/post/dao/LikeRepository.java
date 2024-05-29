package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
    boolean existsByMemberIdAndPostId(Long memberId, Long postId);
}
