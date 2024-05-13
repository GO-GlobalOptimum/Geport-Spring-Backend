package go.glogprototype.domain.post.dao;

import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    Page<PostDto.MainPageResponseDto> postListResponseDtoPage(String keyword, Pageable pageable);
}
