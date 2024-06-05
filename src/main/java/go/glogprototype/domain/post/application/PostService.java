package go.glogprototype.domain.post.application;

import go.glogprototype.domain.notification.application.NotificationService;
import go.glogprototype.domain.post.dao.*;
import go.glogprototype.domain.post.domain.*;
import go.glogprototype.domain.post.dto.CategoryDto;
import go.glogprototype.domain.post.dto.CreatePostRequestDto;
import go.glogprototype.domain.post.dto.CreatePostResponseDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.post.dto.TagDto;
import go.glogprototype.domain.user.dao.UserRepository;
import go.glogprototype.domain.user.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Page<CreatePostResponseDto> findAllPost(String keyword, Pageable pageable,Long memberId) {
        return postRepository.postListResponseDto(keyword, pageable,memberId);
    }

    @Transactional
    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto, Long memberId) {
        Member member = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with memberId: "+memberId));


        // 포스트 생성
        Post post = Post.builder()
                .title(createPostRequestDto.getTitle())
                .postContent(createPostRequestDto.getPostContent())
                .viewsCount(0)
                .isPublic(createPostRequestDto.isPublic())
                .likeCount(createPostRequestDto.getLikeCount())
                .thumbnailImage(createPostRequestDto.getThumbnailImage())
                .isComment(createPostRequestDto.isComment())
                .isDelete(createPostRequestDto.isDelete())
                .member(member)
                .commentCount(createPostRequestDto.getCommentCount())
                .bookMarkCount(createPostRequestDto.getBookMarkCount())
//                .tags(createPostRequestDto.getTags())
                .build();

        //태그 설정
        List<String> tagList = createPostRequestDto.getTags();
        List<TagDto> tagDtoList = new ArrayList<>();
        for(String name : tagList) {
            PostTag tag = PostTag.builder( )
                    .contents(name)
                    .post(post)
                    .is_user(true)
                    .build( );

            tagDtoList.add(TagDto.toTagDto(tag, post));
            post.getTags( ).add(tag);
        }

        // 카테고리 설정
//        post.setCategories(categories);
        List<String> categoryList = createPostRequestDto.getCategories();
        List<CategoryDto> categoryDtos = new ArrayList<>(  );
        for(String categoryName: categoryList) {


            Category category = Category.builder()
                    .name(categoryName)
                    .build();

            CategoryPost categoryPost = CategoryPost.builder( )
                    .category(category)
                    .post(post).build( );

            categoryDtos.add(CategoryDto.toCategoryDto(category,post));
            post.getCategoryPostList().add(categoryPost);

        }

        postRepository.save(post);

        // 응답 DTO 생성
        return  CreatePostResponseDto.toCreatePostResponseDto(post,categoryDtos,tagDtoList);
    }

    public CreatePostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));

        return CreatePostResponseDto.toCreatePostResponseDto(post) ;
    }

//    public List<CreatePostResponseDto> getPostsByIds(List<Long> postIds) {
//        List<Post> posts = postRepository.findAllById(postIds);
//        return posts.stream()
//                .map(CreatePostResponseDto::new)
//                .collect(Collectors.toList());
//    }

    @Transactional
    public Page<CreatePostResponseDto> findAllPostByCategory(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("No category found with ID: " + categoryId));
        return postRepository.postListByCategory(category.getId(), pageable);
    }

    @Transactional
    public Page<CreatePostResponseDto> findAllPostByViews(Pageable pageable) {

        Page<CreatePostResponseDto> findPost = postRepository.findPostByViews(pageable);

        return findPost;
    }

//    @Transactional
//    public Page<CreatePostResponseDto> findAllPostsByUser(String email, Pageable pageable) {
//        Member member = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));
//        return postRepository.findAllByMember(member, pageable)
//                .map(post -> new CreatePostResponseDto(post));
//    }

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
