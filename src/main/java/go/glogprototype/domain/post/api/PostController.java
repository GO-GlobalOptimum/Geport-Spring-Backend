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

    //게시글 작성
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequestDto createPostRequestDto, @AuthenticationPrincipal UserDetails userDetails){
        //log.info("log:", member.toString());
        log.info("userDetails:"+ userDetails.getUsername());  //여기서 말하는 username은 이메일임!

        postService.createPost(createPostRequestDto, userDetails.getUsername());

        return new ResponseEntity<>("success posting", HttpStatus.OK);
    }

    //카테고리별로 게시글 불러오기
    @GetMapping("/list/category")
    public ResponseEntity<Page<FindPostResponseDto>> postListByCategory(@RequestParam Long categoryId, Pageable pageable) {
        Page<FindPostResponseDto> postList = postService.findAllPostByCategory(categoryId, pageable);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    //조회수 기준 인기 게시글 리스트 불러오기
    @GetMapping("/list/popular")
    public Page<FindPostResponseDto> postListByViews(Pageable pageable) {
        return postService.findAllPostByViews(pageable);
    }

    // 특정 사용자 이메일로 작성자의 게시글 리스트 불러오기
    @GetMapping("/list/user")
    public ResponseEntity<Page<FindPostResponseDto>> postListByUser(@RequestParam String email, Pageable pageable) {
        Page<FindPostResponseDto> postList = postService.findAllPostsByUser(email, pageable);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}") // 특정 게시글의 상세 내용 불러오기
    public ResponseEntity<CreatePostResponseDto> getPost(@PathVariable Long postId) {
        CreatePostResponseDto postResponse = postService.getPost(postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //게시글 좋아요 처리
    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {

        postService.likePost(postId, userDetails.getUsername());
        return new ResponseEntity<>("Post liked successfully", HttpStatus.OK);
    }

    //게시글 댓글 처리
    @PostMapping("/{postId}/comment")
    public ResponseEntity<String> commentOnPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails, @RequestParam String comment) {

        postService.commentOnPost(postId, userDetails.getUsername(), comment);
        return new ResponseEntity<>("Comment added successfully", HttpStatus.OK);
    }

}
