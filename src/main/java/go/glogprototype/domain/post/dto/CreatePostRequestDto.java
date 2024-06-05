package go.glogprototype.domain.post.dto;

import go.glogprototype.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreatePostRequestDto {
    private Long id;;
    private String title;
    private String viewsCount;
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
    private List<String> categories;
    private List<String> tags;

    @Builder
    public CreatePostRequestDto(Long id, String title, String viewsCount, String postContent, boolean isPublic, int likeCount, String thumbnailImage, boolean isComment, boolean isDelete, Member member, boolean bookMark, int commentCount, int bookMarkCount, List<String> categories, List<String> tags) {
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
        this.bookMark = bookMark;
        this.commentCount = commentCount;
        this.bookMarkCount = bookMarkCount;
        this.categories = categories;
        this.tags = tags;
    }
}
