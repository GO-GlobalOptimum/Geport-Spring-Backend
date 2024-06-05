package go.glogprototype.domain.post.domain;

import go.glogprototype.domain.user.domain.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private int viewsCount;

    @Column(columnDefinition = "longtext")
    private String postContent;

    private boolean isPublic;

    private int likeCount;

    private String thumbnailImage;

    private boolean isComment;

    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<BookMark> bookMarks = new ArrayList<>();

    private int commentCount;

    private int bookMarkCount;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<CategoryPost> categoryPostList = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<PostTag> tags;

    public void delete() {
        this.isDelete = true;
    }

    @Builder
    public Post(Long id, String title, int viewsCount, String postContent, boolean isPublic, int likeCount, String thumbnailImage, boolean isComment, boolean isDelete, Member member, List<BookMark> bookMarks, int commentCount, int bookMarkCount, List<CategoryPost> categoryPostList, List<PostTag> tags) {
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
        this.bookMarks = bookMarks;
        this.commentCount = commentCount;
        this.bookMarkCount = bookMarkCount;
        this.categoryPostList = categoryPostList;
        this.tags = tags;
    }
}
