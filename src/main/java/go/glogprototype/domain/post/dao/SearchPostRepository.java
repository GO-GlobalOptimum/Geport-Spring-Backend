package go.glogprototype.domain.post.dao;


import go.glogprototype.domain.post.dto.PostDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchPostRepository {
    Page<FindPostResponseDto> postListResponseDto(String keyword, Pageable pageable);
}
