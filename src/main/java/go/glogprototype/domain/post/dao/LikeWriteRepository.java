package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeWriteRepository extends JpaRepository<Like, Long> {
    // 쓰기 작업에 필요한 메서드를 추가할 수 있습니다.
}
