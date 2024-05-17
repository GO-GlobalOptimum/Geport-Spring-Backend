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
        private String post_content;
        private String name;
        private LocalDateTime createdDate;
        private String thumbnail_image;
        private int likeCount;
        private int replyCount;
        private boolean bookMark;

        @Builder
        public FindPostResponseDto(Long id, String title, String post_content, String name, LocalDateTime createdDate, String thumbnail_image, int likeCount, int replyCount, boolean bookMark) {
            this.id = id;
            this.title = title;
            this.post_content = post_content;
            this.name = name;
            this.createdDate = createdDate;
            this.thumbnail_image = thumbnail_image;
            this.likeCount = likeCount;
            this.replyCount = replyCount;
            this.bookMark = bookMark;
        }

        public static FindPostResponseDto toFindPostResponseDto(Post post, Member member){
            return FindPostResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .createdDate(post.getCreatedDate())
                    .post_content(post.getPostContent())
                    .name(member.getName())
                    .thumbnail_image(post.getThumbnailImage())
                    .likeCount(post.getLikeCount())
                    .replyCount(post.getCommentCount())
                    .build();
        }
    }


}
