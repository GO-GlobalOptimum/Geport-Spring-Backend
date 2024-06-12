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
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/spring/posts")
@CrossOrigin(origins = "https://geport.blog")
public class PostController {

    private final PostService postService;

    // 로그인 없이 접근 가능한 테스트 엔드포인트6
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        log.info("테스트 이거 되면 ㄹㅇ 좋겟다");
        return new ResponseEntity<>("This is a test endpoint. No authentication required.", HttpStatus.OK);
    }
    
    //리스트
    @GetMapping("/list")
    public Page<CreatePostResponseDto> postList(@RequestParam(required = false) String keyword, Pageable pageable){
        log.info("test={}", keyword);
        return postService.findAllPost(keyword, pageable,null);
    }

    //게시글 작성
    @PostMapping("/post")
    public ResponseEntity<String> createPost(@RequestBody CreatePostRequestDto createPostRequestDto, @AuthenticationPrincipal UserDetails userDetails){
        //log.info("log:", member.toString());
//        log.info("userDetails:"+ userDetails.getUsername());  //여기서 말하는 username은 이메일임!

        postService.createPost(createPostRequestDto, userDetails.getUsername());

        return new ResponseEntity<>("success posting", HttpStatus.OK);
    }

    //게시글 수정
    @PostMapping("/post-id={postId}/update")
    public void updatePost(@PathVariable Long postId,@RequestBody CreatePostRequestDto createPostRequestDto) {

        postService.updatePost(postId,createPostRequestDto);

    }

    //카테고리별로 게시글 불러오기
    @GetMapping("/list/category-id={categoryId}")
    public ResponseEntity<Page<CreatePostResponseDto>> postListByCategory(@PathVariable Long categoryId, Pageable pageable) {
        Page<CreatePostResponseDto> postList = postService.findAllPostByCategory(categoryId, pageable);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    //조회수 기준 인기 게시글 리스트 불러오기
    @GetMapping("/list/popular")
    public Page<CreatePostResponseDto> postListByViews(Pageable pageable) {
        return postService.findAllPostByViews(pageable);
    }

    //특정 게시글 조회
    @GetMapping("/{postId}") // 특정 게시글의 상세 내용 불러오기
    public ResponseEntity<CreatePostResponseDto> getPost(@PathVariable Long postId) {
        CreatePostResponseDto postResponse = postService.getPost(postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }



    //게시글 좋아요 처리
    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            log.info("User {} is trying to like post {}", userDetails.getUsername(), postId);
            postService.likePost(postId, userDetails.getUsername());
            log.info("User {} successfully liked post {}", userDetails.getUsername(), postId);
            return new ResponseEntity<>("Post liked successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Error liking post: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error liking post: {}", e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //게시글 댓글 처리
    @PostMapping("/{postId}/comment")
    public ResponseEntity<String> commentOnPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails, @RequestParam String comment) {

        postService.commentOnPost(postId, userDetails.getUsername(), comment);
        return new ResponseEntity<>("Comment added successfully", HttpStatus.OK);
    }


    // 나의 게시글 리스트 불러오기
    @GetMapping("/list/my-list")
    public Page<CreatePostResponseDto> myPostList(@CookieValue ("memberId") String memberId,Pageable pageable) {

        Page<CreatePostResponseDto> postList = postService.findAllPost(null,pageable,Long.valueOf(memberId));
        return postList;
    }

    // 특정 사용자 이메일로 작성자의 게시글 리스트 불러오기
    @GetMapping("/list/user-id={userId}")
    public ResponseEntity<Page<CreatePostResponseDto>> postListByUser(@PathVariable Long userId, Pageable pageable) {

        Page<CreatePostResponseDto> postList = postService.findAllPost(null,pageable,userId);

        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    //게시글 삭제
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }
}
