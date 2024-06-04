package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Category;
import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.user.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> ,SearchPostRepository {


    Page<Post> findAllByOrderByViewsCountDesc(Pageable pageable);

    // 카테고리별로 게시글 조회
//    Page<Post> findAllByCategoryOrderByViewsCountDesc(Category category, Pageable pageable);

    // 특정 사용자의 게시글 조회
    Page<Post> findAllByMember(Member member, Pageable pageable);


}
