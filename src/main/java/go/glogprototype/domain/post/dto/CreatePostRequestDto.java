package go.glogprototype.domain.post.dto;

import lombok.Getter;

@Getter
public class CreatePostRequestDto {
    private String title;
    private String content;
    private String thumbnailText;
    private String thumbnailImage;
}
