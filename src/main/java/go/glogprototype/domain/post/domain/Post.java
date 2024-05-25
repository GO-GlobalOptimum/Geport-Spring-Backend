package go.glogprototype.domain.post.domain;

import go.glogprototype.domain.user.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private String thumbnailText;

    private String thumbnailImage;

    private boolean isComment;

    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<BookMark> bookMarks = new ArrayList<>();

    private int commentCount;

    private int bookMarkCount;

    @OneToMany(mappedBy = "post")
    private List<CategoryPost> category;

}
