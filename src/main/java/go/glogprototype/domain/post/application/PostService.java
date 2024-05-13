package go.glogprototype.domain.post.application;

import go.glogprototype.domain.post.dao.PostRepository;
import go.glogprototype.domain.post.dto.PostDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private PostRepository postRepository;

    public Page<PostDto.MainPageResponseDto> findAllPost(String keyword, Pageable pageable) {
        return postRepository.postListResponseDtoPage(keyword, pageable);
    }

}
