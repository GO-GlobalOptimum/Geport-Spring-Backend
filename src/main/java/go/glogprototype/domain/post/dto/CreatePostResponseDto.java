package go.glogprototype.domain.post.dto;

import go.glogprototype.domain.post.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreatePostResponseDto {
    private Long id;
    private String title;
    private String name;
    private String postContent;
    private LocalDateTime createDate;
    private String tags;

    public CreatePostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.name = post.getMember().getName();
        this.postContent = post.getPostContent();
        this.createDate = post.getCreatedAt();
        this.tags = post.getTags();  // 수정된 부분
    }
}
