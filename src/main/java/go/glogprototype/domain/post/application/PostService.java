package go.glogprototype.domain.post.application;

import go.glogprototype.domain.notification.application.NotificationService;
import go.glogprototype.domain.post.dao.*;
import go.glogprototype.domain.post.domain.*;
import go.glogprototype.domain.post.dto.CategoryDto;
import go.glogprototype.domain.post.dto.CreatePostRequestDto;
import go.glogprototype.domain.post.dto.CreatePostResponseDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.post.dto.TagDto;
import go.glogprototype.domain.user.dao.UserReadRepository;
import go.glogprototype.domain.user.dao.UserWriteRepository;
import go.glogprototype.domain.user.domain.Member;
import go.glogprototype.global.config.DataSourceConfig;
import go.glogprototype.global.config.DataSourceContextHolder;
import go.glogprototype.global.config.DataSourceType;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
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
    private final PostTagReadRepository postTagReadRepository;
    private final CategoryReadRepository categoryReadRepository;
    private final NotificationService notificationService;
    private final CommentWriteRepository commentWriteRepository;
    private final LikeWriteRepository likeWriteRepository;
    private final LikeReadRepository likeReadRepository;
    private final CategoryPostWriteRepository categoryPostWriteRepository;
    private final CategoryPostReadRepository categoryPostReadRepository;

    //검색어로 게시글 리스트 조회

    @Transactional(readOnly = true)
    public Page<CreatePostResponseDto> findAllPost(String keyword, Pageable pageable, Long memberId) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        return postReadRepository.postListResponseDto(keyword, pageable, memberId);
    }

    //게시글 생성

    @Transactional
    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto, Long memberId) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
        Member member = userReadRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("No member found with memberId: " + memberId));

//        // 카테고리 찾기
//        List<Category> categories = createPostRequestDto.getCategoryIds().stream()
//                .map(categoryId -> categoryReadRepository.findById(categoryId)
//                        .orElseThrow(() -> new IllegalArgumentException("No category found with ID: " + categoryId)))
//                .collect(Collectors.toList());

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

        postWriteRepository.save(post);

        // 응답 DTO 생성
        return  CreatePostResponseDto.toCreatePostResponseDto(post,categoryDtos,tagDtoList);
    }

    //게시글 하나 조회

    public CreatePostResponseDto getPost(Long postId) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        Post post = postReadRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));

        return CreatePostResponseDto.toCreatePostResponseDto(post);
    }


    //게시글 리스트 조회수 순으로 조회
    @Transactional(readOnly = true)
    public Page<CreatePostResponseDto> findAllPostByViews(Pageable pageable) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        Page<CreatePostResponseDto> findPost = postReadRepository.findPostByViews(pageable);

        return findPost;
    }

    //카테고리 별로 게시글 조회
   @Transactional(readOnly = true)
    public Page<CreatePostResponseDto> findAllPostByCategory(Long categoryId, Pageable pageable) {
       DataSourceContextHolder.setDataSourceType(DataSourceType.READ);
        Category category = categoryReadRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("No category found with ID: " + categoryId));
        return postReadRepository.postListByCategory(category.getId(), pageable);
    }

    //게시글 좋아요 서비스
    @Transactional
    public void likePost(Long postId, String email) {
        // 쓰기 데이터 소스로 설정
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);

        // 게시글 조회 (쓰기 데이터베이스)
        Post post = postWriteRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));

        // 좋아요를 누른 사용자 조회 (읽기 데이터베이스)
        Member liker = userReadRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));

        // 이미 좋아요가 눌렸는지 확인 (읽기 데이터베이스)
        boolean alreadyLiked = likeReadRepository.existsByMemberIdAndPostId(liker.getId(), postId);
        if (alreadyLiked) {
            throw new IllegalArgumentException("Post already liked by this user");
        }

        // 좋아요 엔티티 생성 및 저장 (쓰기 데이터베이스)
        Like like = new Like();
        like.setPost(post);
        like.setMember(liker);
        likeWriteRepository.save(like);

        // 좋아요 수 증가 (쓰기 데이터베이스)
        post.setLikeCount(post.getLikeCount() + 1);
        postWriteRepository.save(post);

        // 게시물 주인 조회
        Member postOwner = post.getMember();
        Long postOwnerId = postOwner.getId();

        // 게시물 주인과 좋아요를 누른 사용자에게 알림 전송
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

    public CreatePostResponseDto updatePost(Long postId, CreatePostRequestDto createPostRequestDto) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
        Post post = postReadRepository.findById(postId).orElseThrow(() -> new RuntimeException());
        List<String> newTagList = createPostRequestDto.getTags();
        List<PostTag> postTagList = postTagReadRepository.findByPost(post);
        postTagWriteRepository.deleteAll(postTagList);

        List<String> newCategoryList = createPostRequestDto.getCategories();
        List<CategoryPost> categoryPostList = categoryPostReadRepository.findByPost(post);

        List<CategoryPost> saveCategoryPostList = new ArrayList<>(  );

        categoryPostWriteRepository.deleteAll(categoryPostList);

        postTagList.clear();
        for(String name : newTagList) {
            PostTag tag = PostTag.builder( )
                    .contents(name)
                    .post(post)
                    .is_user(true)
                    .build( );

            postTagList.add(tag);
        }

        for(String categoryName: newCategoryList) {


            Category category = Category.builder()
                    .name(categoryName)
                    .build();

            CategoryPost categoryPost = CategoryPost.builder( )
                    .category(category)
                    .post(post).build( );

            saveCategoryPostList.add(categoryPost);

        }

        post.update(createPostRequestDto,postTagList,saveCategoryPostList);

        return CreatePostResponseDto.toCreatePostResponseDto(post);

    }

    public String deletePost(Long postId) {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITE);
        Post post = postWriteRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("No post found with id: " + postId));
        postWriteRepository.delete(post);
        return "delete " + postId;
    }
}
