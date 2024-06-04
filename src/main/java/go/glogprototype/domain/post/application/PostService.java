package go.glogprototype.domain.post.application;

import go.glogprototype.domain.notification.application.NotificationService;
import go.glogprototype.domain.post.dao.*;
import go.glogprototype.domain.post.domain.*;
import go.glogprototype.domain.post.dto.CreatePostRequestDto;
import go.glogprototype.domain.post.dto.CreatePostResponseDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.user.dao.UserRepository;
import go.glogprototype.domain.user.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostTagRepository postTagRepository;
    private final CategoryRepository categoryRepository;
    private final NotificationService notificationService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Page<FindPostResponseDto> findAllPost(String keyword, Pageable pageable,Long memberId) {
        return postRepository.postListResponseDto(keyword, pageable,memberId);
    }

    @Transactional
    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto, String email) {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));

        // 카테고리 찾기
        List<Category> categories = createPostRequestDto.getCategoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("No category found with ID: " + categoryId)))
                .collect(Collectors.toList());

        // 포스트 생성
        Post post = Post.builder()
                .title(createPostRequestDto.getTitle())
                .postContent(createPostRequestDto.getContent())
                .thumbnailText(createPostRequestDto.getThumbnailText())
                .thumbnailImage(createPostRequestDto.getThumbnailImage())
                .member(member)
                .tags(createPostRequestDto.getTags())
                .build();

        // 카테고리 설정
//        post.setCategories(categories);

        postRepository.save(post);

        // 태그 설정
        PostTag postTag = new PostTag(createPostRequestDto.getTags(), true);
        postTag.setPost(post); // post 설정
        postTagRepository.save(postTag);

        // 응답 DTO 생성
        return new CreatePostResponseDto(post);
    }

    public CreatePostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));

        return new CreatePostResponseDto(post) ;
    }

    public List<CreatePostResponseDto> getPostsByIds(List<Long> postIds) {
        List<Post> posts = postRepository.findAllById(postIds);
        return posts.stream()
                .map(CreatePostResponseDto::new)
                .collect(Collectors.toList());
    }

//    @Transactional
//    public Page<FindPostResponseDto> findAllPostByCategory(Long categoryId, Pageable pageable) {
//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new IllegalArgumentException("No category found with ID: " + categoryId));
//        return postRepository.findAllByCategoriesOrderByViewsCountDesc(category, pageable)
//                .map(post -> new FindPostResponseDto(post));
//    }

    @Transactional
    public Page<FindPostResponseDto> findAllPostByViews(Pageable pageable) {
        return postRepository.findAllByOrderByViewsCountDesc(pageable)
                .map(post -> new FindPostResponseDto(post));
    }

    @Transactional
    public Page<FindPostResponseDto> findAllPostsByUser(String email, Pageable pageable) {
        Member member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));
        return postRepository.findAllByMember(member, pageable)
                .map(post -> new FindPostResponseDto(post));
    }

    //게시글 좋아요 서비스
    public void likePost(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));

        // 좋아요를 누른 사용자에게 알림
        Member liker = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));

        // 이미 좋아요가 눌렸는지 확인
        boolean alreadyLiked = likeRepository.existsByMemberIdAndPostId(liker.getId(), postId);
        if (alreadyLiked) {
            throw new IllegalArgumentException("Post already liked by this user");
        }

        // 좋아요 엔티티 생성 및 저장
        Like like = new Like();
        like.setPost(post);
        like.setMember(liker);
        likeRepository.save(like);

        // 좋아요 수 증가
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);

        Member postOwner = post.getMember();
        Long postOwnerId = postOwner.getId();


        // 게시물 주인에게 알림 전송
        notificationService.notify(postOwnerId, "Your post received a like!", "like");
        notificationService.notify(liker.getId(), "You liked a post!", "like");
    }

    //게시글 댓글 처리
    public void commentOnPost(Long postId, String email, String commentText) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));

        Member commenter = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMember(commenter);
        comment.setComment_content(commentText);

        // 댓글 저장 로직 추가
        commentRepository.save(comment);

        // 게시물 주인에게 알림 전송
        notificationService.notify(post.getMember().getId(), "Your post received a new comment!", "comment");
    }

    public String deletePost(Long postId){

        Optional<Post> findPost = postRepository.findById(postId);
        postRepository.delete(findPost.orElseThrow( ));

        return "delete"+postId;
    }

}
