package go.glogprototype.domain.post.dto;


import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.post.domain.PostTag;
import lombok.Builder;

public class TagDto {

    private Long id;
    private String tagName;

    private Long postId;

    @Builder
    public TagDto(Long id, String tagName, Long postId) {
        this.id = id;
        this.tagName = tagName;
        this.postId = postId;
    }

    public static TagDto toTagDto(PostTag tag, Post post) {

        return TagDto.builder()
                .tagName(tag.getContents())
                .postId(post.getId()).build();


    }
}
