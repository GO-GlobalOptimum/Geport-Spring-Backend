package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostWriteRepository extends JpaRepository<Post, Long> {
    // 필요한 추가적인 쓰기 작업이 있으면 여기에 정의
}
