package go.glogprototype.domain.post.dto;

import lombok.*;

import java.time.LocalDateTime;



import java.time.LocalDateTime;
@AllArgsConstructor
public class  PostDto{


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MainPageResponseDto {
        private String title;
        private String content;
        private String name;
        private LocalDateTime createDate;
        private String image;
        private int likeCount;
        private int replyCount;
        private boolean bookMark;

        @Builder
        public MainPageResponseDto(String title, String content, String name, LocalDateTime createDate, String image, int likeCount, int replyCount, boolean bookMark) {
            this.title = title;
            this.content = content;
            this.name = name;
            this.createDate = createDate;
            this.image = image;
            this.likeCount = likeCount;
            this.replyCount = replyCount;
            this.bookMark = bookMark;
        }
    }

}
