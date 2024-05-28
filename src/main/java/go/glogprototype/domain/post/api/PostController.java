package go.glogprototype.domain.post.api;

import go.glogprototype.domain.post.application.PostService;
import go.glogprototype.domain.post.dto.CreatePostRequestDto;
import go.glogprototype.domain.post.dto.CreatePostResponseDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("spring/posts")
public class PostController {

    private final PostService postService;

    //리스트
    @GetMapping("/list")
    public Page<FindPostResponseDto> postList(@RequestParam(required = false) String keyword, Pageable pageable){
        log.info("test={}", keyword);
        return postService.findAllPost(keyword, pageable);
    }

//    //게시글 작성
//    @PostMapping("/post")
//    public ResponseEntity<PostWriteDto> postWrite(@RequestBody PostWriteDto postWriteDto){
//        return postService.saveAllPost(postWriteDto);
//    }

    @PostMapping("/post") //게시글 작성
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequestDto createPostRequestDto, @AuthenticationPrincipal UserDetails userDetails){
        //log.info("log:", member.toString());
        log.info("userDetails:"+ userDetails.getUsername());  //여기서 말하는 username은 이메일임!

        postService.createPost(createPostRequestDto, userDetails.getUsername());

        return new ResponseEntity<>("success posting", HttpStatus.OK);
    }

    @GetMapping("/list/category") //카테고리별로 게시글 불러오기
    public ResponseEntity<Page<FindPostResponseDto>> postListByCategory(@RequestParam Long categoryId, Pageable pageable) {
        Page<FindPostResponseDto> postList = postService.findAllPostByCategory(categoryId, pageable);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @GetMapping("/popular") //조회수 기준 인기 게시글 불러오기
    public Page<FindPostResponseDto> postListByViews(Pageable pageable) {
        return postService.findAllPostByViews(pageable);
    }

}
