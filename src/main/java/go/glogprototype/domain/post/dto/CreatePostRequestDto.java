package go.glogprototype.domain.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreatePostRequestDto {
    private String title;
    private String content;
    private String thumbnailText;
    private String thumbnailImage;
    private String tags;
    private List<Long> categoryIds;
}
