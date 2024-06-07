package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Category;
import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.post.domain.PostTag;
import go.glogprototype.domain.user.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReadRepository extends JpaRepository<Post,Long> ,SearchPostRepository {


//    Page<Post> findAllByOrderByViewsCountDesc(Pageable pageable);

    // 카테고리별로 게시글 조회
    //Page<Post> findAllByCategoriesOrderByViewsCountDesc(Category category, Pageable pageable);

    // 특정 사용자의 게시글 조회
//    Page<Post> findAllByMember(Member member, Pageable pageable);




}
