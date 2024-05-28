package go.glogprototype.domain.post.dto;

import go.glogprototype.domain.post.domain.Post;
import go.glogprototype.domain.user.domain.Member;
import lombok.*;

import java.time.LocalDateTime;



import java.time.LocalDateTime;
import java.util.List;

import static go.glogprototype.domain.user.domain.QMember.member;

@Builder
@Data
public class  PostDto{

    @NoArgsConstructor
    @Getter
    public static class FindPostResponseDto {
        private Long id;
        private String title;
        private String postContent;
        private String name;
        private LocalDateTime createdDate;
        private String thumbnailImage;
        private int likeCount;
        private int replyCount;
        private boolean bookMark;
        private int viewsCount;

        @Builder
        public FindPostResponseDto(Long id, String title, String postContent, String name, LocalDateTime createdDate, String thumbnailImage, int likeCount, int replyCount, boolean bookMark) {
            this.id = id;
            this.title = title;
            this.postContent = postContent;
            this.name = name;
            this.createdDate = createdDate;
            this.thumbnailImage = thumbnailImage;
            this.likeCount = likeCount;
            this.replyCount = replyCount;
            this.bookMark = bookMark;
        }

        public FindPostResponseDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.postContent = post.getPostContent();
            this.thumbnailImage = post.getThumbnailImage();
            this.createdDate = post.getCreatedAt();
            this.viewsCount = post.getViewsCount(); // 추가된 필드 초기화
        }

//        public static FindPostResponseDto toFindPostResponseDto(Post post, Member member){
//            return FindPostResponseDto.builder()
//                    .id(post.getId())
//                    .title(post.getTitle())
//                    .createdDate(post.getCreatedDate())
//                    .postContent(post.getPostContent())
//                    .name(member.getName())
//                    .thumbnailImage(post.getThumbnailImage())
//                    .likeCount(post.getLikeCount())
//                    .replyCount(post.getCommentCount())
//                    .build();
//        }
    }


}
