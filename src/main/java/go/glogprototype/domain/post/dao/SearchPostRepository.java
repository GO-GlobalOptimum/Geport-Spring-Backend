package go.glogprototype.domain.post.dao;


import go.glogprototype.domain.post.domain.Category;
import go.glogprototype.domain.post.dto.CreatePostResponseDto;
import go.glogprototype.domain.post.dto.PostDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchPostRepository {
    Page<CreatePostResponseDto> postListResponseDto(String keyword, Pageable pageable, Long memberId);

    Page<CreatePostResponseDto> postListByCategory(Long categoryId, Pageable pageable);

    Page<CreatePostResponseDto> findPostByViews(Pageable pageable);
}
