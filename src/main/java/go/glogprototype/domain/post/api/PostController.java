package go.glogprototype.domain.post.api;

import go.glogprototype.domain.post.application.PostService;
import go.glogprototype.domain.post.dao.PostRepository;
import go.glogprototype.domain.post.dto.PostDto;
import go.glogprototype.domain.user.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class PostController {

    private PostService postService;

    private MemberRepository memberRepository;
    private PostRepository postRepository;

    @GetMapping
    public List<PostDto.MainPageResponseDto> postList() {

        List<PostDto.MainPageResponseDto> responseList = new ArrayList<PostDto.MainPageResponseDto>();

        return responseList;
    }

    @GetMapping("/list")
    public Page<PostDto.MainPageResponseDto> postList(@RequestParam(required = false) String keyword, Pageable pageable){
        return postService.findAllPost(keyword, pageable);
    }
}
