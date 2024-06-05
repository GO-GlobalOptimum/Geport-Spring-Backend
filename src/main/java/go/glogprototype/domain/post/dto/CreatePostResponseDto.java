package go.glogprototype.domain.post.dto;

import go.glogprototype.domain.post.domain.Category;
import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreatePostResponseDto {
    private Long id;;
    private String title;
    private int viewsCount;
    private String postContent;
    private boolean isPublic;
    private int likeCount;
    private String thumbnailImage;
    private boolean isComment;
    private boolean isDelete;
    private Member member;
    private boolean bookMark;
    private int commentCount;
    private int bookMarkCount;
    private List<CategoryDto> categories;
    private List<TagDto> tags;

    @Builder
    public CreatePostResponseDto(Long id, String title, int viewsCount, String postContent, boolean isPublic, int likeCount, String thumbnailImage, boolean isComment, boolean isDelete, Member member , int commentCount, int bookMarkCount, List<CategoryDto> categories, List<TagDto> tags) {
        this.id = id;
        this.title = title;
        this.viewsCount = viewsCount;
        this.postContent = postContent;
        this.isPublic = isPublic;
        this.likeCount = likeCount;
        this.thumbnailImage = thumbnailImage;
        this.isComment = isComment;
        this.isDelete = isDelete;
        this.member = member;
        this.commentCount = commentCount;
        this.bookMarkCount = bookMarkCount;
        this.categories = categories;
        this.tags = tags;
    }

    public static CreatePostResponseDto toCreatePostResponseDto(Post post,List<CategoryDto> categoryDtos, List<TagDto> tagDtos) {

        return CreatePostResponseDto.builder()
                .title(post.getTitle())
                .viewsCount(post.getViewsCount())
                .postContent(post.getPostContent())
                .isPublic(post.isPublic())
                .likeCount(post.getLikeCount())
                .thumbnailImage(post.getThumbnailImage())
                .isComment(post.isComment())
                .isDelete(post.isDelete())
                .member(post.getMember())
                .commentCount(post.getCommentCount())
                .categories(categoryDtos)
                .tags(tagDtos)
                .build();

    }

    public static CreatePostResponseDto toCreatePostResponseDto(Post post) {

        return CreatePostResponseDto.builder()
                .title(post.getTitle())
                .viewsCount(post.getViewsCount())
                .postContent(post.getPostContent())
                .isPublic(post.isPublic())
                .likeCount(post.getLikeCount())
                .thumbnailImage(post.getThumbnailImage())
                .isComment(post.isComment())
                .isDelete(post.isDelete())
                .member(post.getMember())
                .commentCount(post.getCommentCount())
                .build();

    }



}
