package go.glogprototype.domain.post.dto;

import go.glogprototype.domain.post.domain.BookMark;
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
        ;
        private String title;
        private String viewsCount;
        private String postContent;
        private Boolean isPublic;
        private int likeCount;
        private String thumbnailImage;
        private Boolean isComment;
        private Boolean isDelete;
        private Member member;
        private boolean bookMark;
        private int commentCount;
        private int bookMarkCount;
        private List<CategoryDto> categoryDtos;
        private List<TagDto> tagDtos;

        @Builder
        public FindPostResponseDto(Long id, String title, String viewsCount, String postContent, Boolean isPublic, int likeCount, String thumbnailImage, Boolean isComment, Boolean isDelete, Member member, boolean bookMark, int commentCount, int bookMarkCount, List<CategoryDto> categoryDtos, List<TagDto> tagDtos) {
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
            this.categoryDtos = categoryDtos;
            this.tagDtos = tagDtos;
        }


//        public static FindPostResponseDto toFindPostResponseDto(Post post, Member member){
//            return FindPostResponseDto.builder()
//                    .id(post.getId())
//                    .title(post.getTitle())
//                    .postContent(post.getPostContent())
//                    .name(member.getName())
//                    .thumbnailImage(post.getThumbnailImage())
//                    .likeCount(post.getLikeCount())
//                    .replyCount(post.getCommentCount())
//                    .build();
//        }
//    }

    }
}

