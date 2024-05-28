package go.glogprototype.domain.post.application;

import go.glogprototype.domain.post.dao.PostRepository;
import go.glogprototype.domain.post.dao.PostTagRepository;
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

    @Transactional
    public Page<FindPostResponseDto> findAllPost(String keyword, Pageable pageable) {
        return postRepository.postListResponseDto(keyword, pageable);
    }

    @Transactional
    public CreatePostResponseDto createPost(CreatePostRequestDto createPostRequestDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No member found with email: " + email));

        Post post = new Post(createPostRequestDto.getTitle(), createPostRequestDto.getContent(), createPostRequestDto.getThumbnailText(), createPostRequestDto.getThumbnailImage(), member, createPostRequestDto.getTags());
        postRepository.save(post);

        PostTag postTag = new PostTag(createPostRequestDto.getTags(),true);
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




//    @Transactional
//    public ResponseEntity<PostWriteDto> saveAllPost(PostWriteDto postWriteDto) {
//        // 해당 유저가 멤버 테이블에 존재하는지 확인한다.
//        Member member = memberRepository.findById(postWriteDto.getMemberId())
//                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
//
//        Post post = Post.builder()
//                .member(member)
//                .title(postWriteDto.getTitle())
//                .postContent(postWriteDto.getPostContent())
//                .thumbnailImage(postWriteDto.getThumbnailImage())
//                .isPublic(postWriteDto.isPublic())
////                .category(postWriteDto.getCategories())
//                .build();
//        //category 는?
//
//        // 엔티티를 데이터베이스에 저장
//        Post savedPost = postRepository.save(post);
//
//
//        PostWriteDto responseDto = PostWriteDto.builder()
//                .memberId(savedPost.getMember().getId())
//                .title(savedPost.getTitle())
//                .postContent(savedPost.getPostContent())
//                .thumbnailImage(savedPost.getThumbnailImage())
//                .createdDate(savedPost.getCreatedDate())
//                .isPublic(savedPost.isPublic())
//                .build();
//
//
//
//        return ResponseEntity.ok(responseDto);
//
//    }


}
