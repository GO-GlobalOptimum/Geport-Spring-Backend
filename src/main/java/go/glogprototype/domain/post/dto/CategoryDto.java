package go.glogprototype.domain.post.dto;

import go.glogprototype.domain.post.domain.Category;
import go.glogprototype.domain.post.domain.Post;
import lombok.Builder;

public class CategoryDto {

    private Long id;

    private String name;

    private Long postId;

    @Builder
    public CategoryDto(Long id, String name, Long postId) {
        this.id = id;
        this.name = name;
        this.postId = postId;
    }

    public static CategoryDto toCategoryDto(Category category, Post post) {

        return CategoryDto.builder()
                .postId(post.getId())
                .name(category.getName( )).build();

    }
}
