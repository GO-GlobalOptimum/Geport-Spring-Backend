package go.glogprototype.domain.post.application;

import go.glogprototype.domain.post.dao.CategoryPostRepository;
import go.glogprototype.domain.post.dao.CategoryRepository;
import go.glogprototype.domain.post.dao.PostRepository;
import go.glogprototype.domain.post.dao.PostTagRepository;
import go.glogprototype.domain.post.domain.Category;
import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.post.domain.PostTag;
import go.glogprototype.domain.post.dto.CreatePostRequestDto;
import go.glogprototype.domain.post.dto.CreatePostResponseDto;
import go.glogprototype.domain.post.dto.PostDto.*;
import go.glogprototype.domain.post.dto.PostWriteDto;
import go.glogprototype.domain.user.dao.MemberRepository;
import go.glogprototype.domain.user.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostTagRepository postTagRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Page<FindPostResponseDto> findAllPost(String keyword, Pageable pageable) {
        return postRepository.postListResponseDto(keyword, pageable);
    }

    @Transactional
    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto, String email) {
        Member member = memberRepository.findByEmail(email)
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
        post.setCategories(categories);
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

        return new CreatePostResponseDto(post);
    }

    public List<CreatePostResponseDto> getPostsByIds(List<Long> postIds) {
        List<Post> posts = postRepository.findAllById(postIds);
        return posts.stream()
                .map(CreatePostResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<FindPostResponseDto> findAllPostByCategory(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("No category found with ID: " + categoryId));
        return postRepository.findAllByCategoriesOrderByViewsCountDesc(category, pageable)
                .map(post -> new FindPostResponseDto(post));
    }

    @Transactional
    public Page<FindPostResponseDto> findAllPostByViews(Pageable pageable) {
        return postRepository.findAllByOrderByViewsCountDesc(pageable)
                .map(post -> new FindPostResponseDto(post));
    }

    @Transactional
    public Page<FindPostResponseDto> findAllPostsByUser(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));
        return postRepository.findAllByMember(member, pageable)
                .map(post -> new FindPostResponseDto(post));
    }
}
