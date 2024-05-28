package go.glogprototype.domain.post.dto;

import go.glogprototype.domain.post.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostWriteDto {
    private Long memberId; // 작성자 ID
    private String userName; // 작성자 이름
    private String title; // 게시글 제목
    private String postContent; // 게시글 내용
    private String thumbnailImage; // 썸네일 이미지
    private LocalDateTime createdDate; // 작성 날짜
    private boolean isPublic; // 공개 여부
    private List<Category> categories; // 카테고리 목록

    @Builder
    public PostWriteDto(Long memberId, String userName, String title, String postContent, String thumbnailImage, LocalDateTime createdDate, boolean isPublic, List<Category> categories) {
        this.memberId = memberId;
        this.userName = userName;
        this.title = title;
        this.postContent = postContent;
        this.thumbnailImage = thumbnailImage;
        this.createdDate = createdDate;
        this.isPublic = isPublic;
        this.categories = categories;
    }
}
