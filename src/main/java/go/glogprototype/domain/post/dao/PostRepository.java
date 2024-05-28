package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Category;
import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.post.dto.PostDto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> ,SearchPostRepository {

    Page<FindPostResponseDto> postListResponseDto(String keyword, Pageable pageable);
    Page<Post> findAllByOrderByViewsCountDesc(Pageable pageable);

    // 카테고리별로 게시글 조회
    Page<Post> findAllByCategoriesOrderByViewsCountDesc(Category category, Pageable pageable);

}
