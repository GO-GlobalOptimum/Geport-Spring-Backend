package go.glogprototype.domain.post.application;

import go.glogprototype.domain.notification.application.NotificationService;
import go.glogprototype.domain.post.dao.*;
import go.glogprototype.domain.post.domain.*;
import go.glogprototype.domain.post.dto.CreatePostRequestDto;
import go.glogprototype.domain.post.dto.CreatePostResponseDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.user.dao.UserReadRepository;
import go.glogprototype.domain.user.dao.UserWriteRepository;
import go.glogprototype.domain.user.domain.Member;
import go.glogprototype.global.config.DataSourceContextHolder;
import go.glogprototype.global.config.DataSourceType;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostWriteRepository postWriteRepository;
    private final PostReadRepository postReadRepository;
    private final UserReadRepository userReadRepository;
    private final UserWriteRepository userWriteRepository;
    private final PostTagWriteRepository postTagWriteRepository;
    private final CategoryReadRepository categoryReadRepository;
    private final NotificationService notificationService;
    private final CommentWriteRepository commentWriteRepository;
    private final LikeWriteRepository likeWriteRepository;
    private final LikeReadRepository likeReadRepository;

    @Transactional(readOnly = true)
    public Page<FindPostResponseDto> findAllPost(String keyword, Pageable pageable, Long memberId) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        return postReadRepository.postListResponseDto(keyword, pageable, memberId);
    }

    @Transactional
    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto, String email) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
        Member member = userReadRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));

        // 카테고리 찾기
        List<Category> categories = createPostRequestDto.getCategoryIds().stream()
                .map(categoryId -> categoryReadRepository.findById(categoryId)
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

        postWriteRepository.save(post);

        // 태그 설정
        PostTag postTag = new PostTag(createPostRequestDto.getTags(), true);
        postTag.setPost(post); // post 설정
        postTagWriteRepository.save(postTag);

        // 응답 DTO 생성
        return new CreatePostResponseDto(post);
    }

    public CreatePostResponseDto getPost(Long postId) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        Post post = postReadRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));

        return new CreatePostResponseDto(post);
    }

    public List<CreatePostResponseDto> getPostsByIds(List<Long> postIds) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        List<Post> posts = postReadRepository.findAllById(postIds);
        return posts.stream()
                .map(CreatePostResponseDto::new)
                .collect(Collectors.toList());
    }

//    @Transactional(readOnly = true)
//    public Page<FindPostResponseDto> findAllPostByCategory(Long categoryId, Pageable pageable) {
//        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
//        Category category = categoryReadRepository.findById(categoryId)
//                .orElseThrow(() -> new IllegalArgumentException("No category found with ID: " + categoryId));
//        return postReadRepository.findAllByCategoriesOrderByViewsCountDesc(category, pageable)
//                .map(post -> new FindPostResponseDto(post));
//    }

    @Transactional(readOnly = true)
    public Page<FindPostResponseDto> findAllPostByViews(Pageable pageable) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        return postReadRepository.findAllByOrderByViewsCountDesc(pageable)
                .map(post -> new FindPostResponseDto(post));
    }

    @Transactional(readOnly = true)
    public Page<FindPostResponseDto> findAllPostsByUser(String email, Pageable pageable) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        Member member = userReadRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));
        return postReadRepository.findAllByMember(member, pageable)
                .map(post -> new FindPostResponseDto(post));
    }

    public void likePost(Long postId, String email) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
        Post post = postReadRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));

        Member liker = userReadRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));

        boolean alreadyLiked = likeReadRepository.existsByMemberIdAndPostId(liker.getId(), postId);
        if (alreadyLiked) {
            throw new IllegalArgumentException("Post already liked by this user");
        }

        Like like = new Like();
        like.setPost(post);
        like.setMember(liker);
        likeWriteRepository.save(like);

        post.setLikeCount(post.getLikeCount() + 1);
        postWriteRepository.save(post);

        Member postOwner = post.getMember();
        Long postOwnerId = postOwner.getId();

        notificationService.notify(postOwnerId, "Your post received a like!", "like");
        notificationService.notify(liker.getId(), "You liked a post!", "like");
    }

    public void commentOnPost(Long postId, String email, String commentText) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
        Post post = postReadRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));

        Member commenter = userReadRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMember(commenter);
        comment.setComment_content(commentText);

        commentWriteRepository.save(comment);

        notificationService.notify(post.getMember().getId(), "Your post received a new comment!", "comment");
    }

    public String deletePost(Long postId) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
        Post post = postWriteRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));
        postWriteRepository.delete(post);
        return "delete " + postId;
    }
}
